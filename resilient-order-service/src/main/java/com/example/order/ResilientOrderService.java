/*
 * Spring Boot Microservices tutorial - Resilient order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import reactor.core.publisher.Mono;

@Service
public class ResilientOrderService {
    private static final Logger log = LogManager.getLogger(ResilientOrderService.class);
    private static final String URI_USER_BY_ID = "http://user-service/api/users/{id}";

    private final WebClient web;
    private final Retry retry;

    public ResilientOrderService(WebClient web, RetryRegistry registry) {
        this.web = web;
        this.retry = registry.retry("userService");
    }

    public Mono<UserDTO> getUser(Long id) {
        log.traceEntry("getUser({})", id);

        return web.get().uri(URI_USER_BY_ID, id).retrieve().bodyToMono(UserDTO.class).transformDeferred(RetryOperator.of(retry))
                .doOnError(ex -> log.error("Failed to check user service after retries: {}", ex.getMessage()));
    }
}
