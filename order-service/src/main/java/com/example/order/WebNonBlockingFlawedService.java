/*
 * Spring Boot Microservices tutorial - Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class WebNonBlockingFlawedService {
    private static final String SVC_ID = "user-service";
    private static final String SVC_URI = "/api/users/";
    private static final Logger log = LogManager.getLogger(WebNonBlockingFlawedService.class);

    private final OrderRepository repo;
    private final WebClient client;

    /**
     * Injecting the required beans
     * 
     * @param repo
     * @param builder it needs to be qualified to resolve ambiguity
     */
    public WebNonBlockingFlawedService(OrderRepository repo, WebClient.Builder builder) {
        this.repo = repo;
        this.client = builder.build();
    }

    /**
     * The user should exist to save an order for it
     */
    public Mono<Order> save(Order order) {
        log.traceEntry("save({})", order);

        return client.get().uri("http://" + SVC_ID + SVC_URI + order.getUserId()).retrieve().toBodilessEntity()
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
                    log.warn("Attempted to create order for non-existing user {}", order.getUserId());
                    return Mono.error(new UserNotFoundException(order.getUserId(), ex));
                }).onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("Failed to invoke the user service", ex);
                    return Mono.error(new ServiceUnavailableException("User service is not available", ex));
                }).flatMap(_ -> {
                    // TODO: BAD !!! just() is blocking !!! 
                    return Mono.just(repo.save(order));
                });
    }
}
