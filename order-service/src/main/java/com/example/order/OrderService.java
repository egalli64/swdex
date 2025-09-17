/*
 * Spring Boot Microservices tutorial - Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {
    private static final String USER_URI = "/api/users/";
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private final OrderRepository repo;
    private final DiscoveryClient discovery;
    private final RestClient rest;

    public OrderService(OrderRepository repo, DiscoveryClient discovery, RestClient.Builder builder) {
        this.repo = repo;
        this.discovery = discovery;
        this.rest = builder.build();
    }

    public List<Order> getAll() {
        log.traceEntry("getAll()");
        return repo.findAll();
    }

    public Optional<Order> get(Long id) {
        log.traceEntry("getById({})", id);
        return repo.findById(id);
    }

    /**
     * The user should exist to save an order for it
     */
    public Order save(Order order) {
        log.traceEntry("save({})", order);

        List<ServiceInstance> instances = discovery.getInstances("user-service");
        if (instances.isEmpty()) {
            log.error("User service not found");
            throw new ServiceUnavailableException("User service is not available");
        }

        ServiceInstance svc = instances.get(0);

        try {
            rest.get().uri(svc.getUri() + USER_URI + order.getUserId()).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            log.warn("Attempted to create order for non-existing user {}", order.getUserId());
            throw new UserNotFoundException(order.getUserId(), ex);
        }

        return repo.save(order);
    }

    public void delete(Long id) {
        log.traceEntry("delete({})", id);
        repo.deleteById(id);
    }
}
