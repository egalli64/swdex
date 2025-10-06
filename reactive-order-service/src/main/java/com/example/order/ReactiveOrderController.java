/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Using a WebClient in asynchronous (non-blocking) way
 */
@RestController
@RequestMapping("/api/reactive-orders")
public class ReactiveOrderController {
    private static Logger log = LogManager.getLogger(ReactiveOrderController.class);

    private final ReactiveOrderService svc;

    public ReactiveOrderController(ReactiveOrderService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -X POST http://localhost:8080/api/reactive-orders ^
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

    /**
     * <pre>
        curl -i -X GET http://localhost:8080/api/reactive-orders/123/username
     * </pre>
     */
    @GetMapping("/{id}/username")
    public Mono<String> getOrderUserName(@PathVariable Long id) {
        log.traceEntry("getOrderUserName({})", id);

        return svc.getUserName(id).switchIfEmpty(Mono.error(new OrderNotFoundException(id)));
    }

    /**
     * Server-Sent Events (SSE) is used here. The response contains a stream of
     * data. It is divided in chunks, where each chunk contains 1+ SSE, that, in our
     * case, is an order.
     * <pre>
        curl -N http://localhost:8080/api/reactive-orders/stream
     * </pre>
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReactiveOrder> streamAllOrders() {
        return svc.streamAll();
    }
}
