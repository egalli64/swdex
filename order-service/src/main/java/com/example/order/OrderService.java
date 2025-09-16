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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    private static final String USER_URI = "http://user-service/api/users/";
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private final OrderRepository repo;
    private final RestTemplate rest;

    public OrderService(OrderRepository repo, RestTemplate rest) {
        this.repo = repo;
        this.rest = rest;
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

        try {
            rest.getForObject(USER_URI + order.getUserId(), Object.class);
        } catch (Exception ex) {
            log.warn("User not found", ex);
            throw new UserNotFoundException(order.getUserId());
        }

        return repo.save(order);
    }

    public void delete(Long id) {
        log.traceEntry("delete({})", id);
        repo.deleteById(id);
    }
}
