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

@Service
public class OrderService {
    private static Logger log = LogManager.getLogger(OrderService.class);
    private OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public List<Order> getAll() {
        log.traceEntry("getAll()");
        return repo.findAll();
    }

    public Optional<Order> get(Long id) {
        log.traceEntry("getById({})", id);
        return repo.findById(id);
    }

    public Order save(Order order) {
        log.traceEntry("save({})", order);
        return repo.save(order);
    }

    public void delete(Long id) {
        log.traceEntry("delete({})", id);
        repo.deleteById(id);
    }
}
