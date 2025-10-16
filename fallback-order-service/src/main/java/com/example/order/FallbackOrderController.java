/*
 * Spring Boot Microservices tutorial - Fallback Order Service
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
 * Using a WebClient w/ fallback protected resiliency
 */
@RestController
@RequestMapping("/api/fallback-orders")
public class FallbackOrderController {
    private static Logger log = LogManager.getLogger(FallbackOrderController.class);

    private final FallbackOrderService svc;

    public FallbackOrderController(FallbackOrderService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -i -X GET http://localhost:8080/api/fallback-orders/1/user
     * </pre>
     */
    @GetMapping("/{id}/user")
    public Mono<UserDTO> getOrderUserReactive(@PathVariable Long id) {
        log.traceEntry("getOrderUserReactive({})", id);

        return svc.getUser(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }
}
