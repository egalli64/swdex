/*
 * Spring Boot Microservices tutorial - Bulkhead Order Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.bulkhead.operator.BulkheadOperator;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import reactor.core.publisher.Mono;

@Service
public class BulkheadOrderService {
    private static final Logger log = LogManager.getLogger(BulkheadOrderService.class);
    private static final String GET_USER_BY_ID = "http://user-service/api/users/{id}";

    private final WebClient web;
    private final Retry retry;
    private final CircuitBreaker breaker;
    private final Bulkhead bulkhead;
    private final Map<Long, UserDTO> cache;

    public BulkheadOrderService(WebClient web, RetryRegistry retryRegistry, CircuitBreakerRegistry breakerRegistry,
            BulkheadRegistry bulkheadRegistry) {
        this.web = web;
        this.retry = retryRegistry.retry("userService");
        this.breaker = breakerRegistry.circuitBreaker("userService");
        this.bulkhead = bulkheadRegistry.bulkhead("userService");
        this.cache = new ConcurrentHashMap<>();

        // simulate a previous successful get for a user
        cache.put(1L, new UserDTO(1L, "Tom", "tom@example.net"));
    }

    public Mono<UserDTO> getUser(Long id) {
        log.traceEntry("getUser({})", id);

        return web.get().uri(GET_USER_BY_ID, id).retrieve().bodyToMono(UserDTO.class) //
                .doOnNext(user -> cache.put(id, user)) //
                .transformDeferred(RetryOperator.of(retry)) //
                .transformDeferred(BulkheadOperator.of(bulkhead)) //
                .transformDeferred(CircuitBreakerOperator.of(breaker)) //
                .onErrorResume(ex -> handleFailure(id, ex)); //
    }

    /**
     * Manage a failure for the user with the given id
     */
    private Mono<UserDTO> handleFailure(Long id, Throwable ex) {
        if (ex instanceof CallNotPermittedException) {
            log.warn("Fallback for user service on open circuit breaker");
        } else if (ex instanceof BulkheadFullException) {
            log.warn("Fallback on full bulkhead calling for user service " + id);
        } else {
            log.error("Fallback on call to user service failure: " + ex.getMessage());
        }

        return Mono.justOrEmpty(cache.get(id))
                .switchIfEmpty(Mono.just(new UserDTO(id, "User Unknown", "anonymous@example.com")))
                .doOnNext(_ -> log.info("Returning fallback user w/ id " + id));
    }
}
