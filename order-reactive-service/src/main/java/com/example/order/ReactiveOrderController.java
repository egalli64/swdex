/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * Using a WebClient in asynchronous (non-blocking) way
 */
@RestController
@RequestMapping("/api/reactive-orders/")
public class ReactiveOrderController {
    private static Logger log = LogManager.getLogger(ReactiveOrderController.class);

    private final ReactiveOrderService svc;

    public ReactiveOrderController(ReactiveOrderService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -X POST http://localhost:8080/api/reactive-orders/ ^
        -H "Content-Type: application/json" ^
        -d "{\"userId\": 1, \"productId\": 42, \"quantity\": 6, \"status\": \"pending\"}"
     * </pre>
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReactiveOrder> create(@RequestBody ReactiveOrder order) {
        log.traceEntry("create({})", order);
        return svc.save(order);
    }
}
