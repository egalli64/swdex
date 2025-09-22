/*
 * Spring Boot Microservices tutorial - Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class LoadBalancedService {
    private static final String SVC_ID = "user-service";
    private static final String SVC_URI = "/api/users/";
    private static final Logger log = LogManager.getLogger(LoadBalancedService.class);

    private final OrderRepository repo;
    private final RestClient client;

    /**
     * Injecting the required beans
     * 
     * @param repo
     * @param builder it needs to be qualified to resolve ambiguity
     */
    public LoadBalancedService(OrderRepository repo,
            @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder builder) {
        this.repo = repo;
        this.client = builder.build();
    }

    /**
     * The user should exist to save an order for it
     */
    public Order save(Order order) {
        log.traceEntry("save({})", order);

        try {
            client.get().uri("http://" + SVC_ID + SVC_URI + order.getUserId()).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            log.warn("Attempted to create order for non-existing user {}", order.getUserId());
            throw new UserNotFoundException(order.getUserId(), ex);
        } catch (RestClientException ex) {
            log.error("Failed to invoke the user service", ex);
            throw new ServiceUnavailableException("User service is not available", ex);
        }

        return repo.save(order);
    }
}
