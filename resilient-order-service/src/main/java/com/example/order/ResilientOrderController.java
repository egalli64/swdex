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
 * Using a WebClient in a resilient
 */
@RestController
@RequestMapping("/api/resilient-orders")
public class ResilientOrderController {
    private static Logger log = LogManager.getLogger(ResilientOrderController.class);

    private final ResilientOrderService svc;

    public ResilientOrderController(ResilientOrderService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -i -X GET http://localhost:8080/api/resilient-orders/1/user
     * </pre>
     */
    @GetMapping("/{id}/user")
    public Mono<UserDTO> getOrderUser(@PathVariable Long id) {
        log.traceEntry("getOrderUser({})", id);

        return svc.getUser(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }
}
