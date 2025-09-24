/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class ReactiveOrderService {
    private static final String URI_USER_BY_ID = "http://user-service/api/users/{id}";
    private static final Logger log = LogManager.getLogger(ReactiveOrderService.class);

    private final ReactiveOrderRepository repo;
    private final WebClient client;

    /**
     * Injecting the required beans
     * 
     * @param repo
     * @param builder it needs to be qualified to resolve ambiguity
     */
    public ReactiveOrderService(ReactiveOrderRepository repo, WebClient.Builder builder) {
        this.repo = repo;
        this.client = builder.build();
    }

    /**
     * The user should exist to save an order for it
     */
    public Mono<ReactiveOrder> save(ReactiveOrder order) {
        log.traceEntry("save({})", order);

        return clientGetUser(order).toBodilessEntity() //
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
                    log.warn("Attempted to create order for non-existing user {}", order.getUserId());
                    return Mono.error(new UserNotFoundException(order.getUserId(), ex));
                }).onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("Failed to invoke the user service", ex);
                    return Mono.error(new ServiceUnavailableException("User service is not available", ex));
                }).flatMap(_ -> repo.save(order));
    }

    /**
     * <ul>
     * <li>Use the repository to load in memory the required order</li>
     * <li>Flat map the order to user by WebClient</li>
     * <li>Map (synchronous) to extract the name from the user</li>
     * </ul>
     */
    public Mono<String> getUserName(Long orderId) {
        log.traceEntry("getUserName({})", orderId);

        return repo.findById(orderId) //
                .flatMap(order -> clientGetUser(order).bodyToMono(UserDTO.class)
                        .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
                            log.warn("Looking for non-existing user {}", order.getUserId());
                            return Mono.error(new UserNotFoundException(order.getUserId(), ex));
                        }).onErrorResume(WebClientResponseException.class, ex -> {
                            log.error("Failed to invoke the user service", ex);
                            return Mono.error(new ServiceUnavailableException("User service is not available", ex));
                        })) //
                .map(user -> user.name());
    }

    private ResponseSpec clientGetUser(ReactiveOrder order) {
        log.traceEntry("clientGetUser({})", order);

        return client.get().uri(URI_USER_BY_ID, order.getUserId()).retrieve();
    }
}
