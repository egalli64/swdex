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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static Logger log = LogManager.getLogger(OrderController.class);
    private OrderService svc;

    public OrderController(OrderService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Order> getAll() {
        log.traceEntry("getAll()");
        return svc.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        log.traceEntry("getById({})", id);
        Optional<Order> order = svc.get(id);
        return order.isPresent() ? ResponseEntity.ok(order.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        log.traceEntry("create({})", order);
        return svc.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order newer) {
        log.traceEntry("update({}, {})", id, newer);
        Optional<Order> opt = svc.get(id);
        if (opt.isPresent()) {
            Order order = opt.get();
            order.setUserId(newer.getUserId());
            order.setProductId(newer.getProductId());
            order.setQuantity(newer.getQuantity());
            order.setStatus(newer.getStatus());
            return ResponseEntity.ok(svc.save(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.traceEntry("delete({})", id);
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
