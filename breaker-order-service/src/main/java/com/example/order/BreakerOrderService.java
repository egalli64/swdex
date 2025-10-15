/*
 * Spring Boot Microservices tutorial - Circuit Breaker Order Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import reactor.core.publisher.Mono;

@Service
public class BreakerOrderService {
    private static final Logger log = LogManager.getLogger(BreakerOrderService.class);
    private static final String GET_USER_BY_ID = "http://user-service/api/users/{id}";

    private final WebClient web;
    private final Retry retry;
    private final CircuitBreaker breaker;

    public BreakerOrderService(WebClient web, RetryRegistry retryRegistry, CircuitBreakerRegistry breakerRegistry) {
        this.web = web;
        this.retry = retryRegistry.retry("userService");
        this.breaker = breakerRegistry.circuitBreaker("userService");
    }

    public Mono<UserDTO> getUser(Long id) {
        log.traceEntry("getUser({})", id);

        return web.get().uri(GET_USER_BY_ID, id).retrieve().bodyToMono(UserDTO.class)
                .transformDeferred(RetryOperator.of(retry)) //
                .transformDeferred(CircuitBreakerOperator.of(breaker)) //
                .doOnError(CallNotPermittedException.class, ex -> log.warn("Open circuit breaker: " + ex.getMessage()))
                .doOnError(ex -> log.error("Failed to check user service after retries: " + ex.getMessage()));
    }
}
