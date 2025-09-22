/*
 * Spring Boot Microservices tutorial - Order service
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

@RestController
@RequestMapping("/api/orders/lb")
public class LoadBalancedController {
    private static Logger log = LogManager.getLogger(LoadBalancedController.class);

    private final LoadBalancedService svc;

    public LoadBalancedController(LoadBalancedService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -X POST http://localhost:8080/api/orders/lb ^
        -H "Content-Type: application/json" ^
        -d "{\"userId\": 1, \"productId\": 42, \"quantity\": 6, \"status\": \"pending\"}"
     * </pre>
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order) {
        log.traceEntry("create({})", order);
        return svc.save(order);
    }
}
