/*
 * Spring Boot Microservices tutorial - Resilient order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * Using a WebClient in resilient way
 */
@RestController
@RequestMapping("/api/resilient-orders/reactive")
public class ReactiveOrderController {
    private static Logger log = LogManager.getLogger(ReactiveOrderController.class);

    private final ReactiveOrderService svc;

    public ReactiveOrderController(ReactiveOrderService reactiveSvc) {
        this.svc = reactiveSvc;
    }

    /**
     * <pre>
        curl -i -X GET http://localhost:8080/api/resilient-orders/reactive/1/user
     * </pre>
     */
    @GetMapping("/{id}/user")
    public Mono<UserDTO> getOrderUserReactive(@PathVariable Long id) {
        log.traceEntry("getOrderUserReactive({})", id);

        return svc.getUser(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }
}
