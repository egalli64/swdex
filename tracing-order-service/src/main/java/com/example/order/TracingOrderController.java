/*
 * Spring Boot Microservices tutorial - Tracing Order Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tracing-orders")
public class TracingOrderController {
    private static Logger log = LogManager.getLogger(TracingOrderController.class);

    private final TracingOrderService svc;

    public TracingOrderController(TracingOrderService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/tracing-orders
     * </pre>
     */
    @GetMapping()
    public Mono<Integer> getOrderInfo() {
        log.trace("Enter getOrderInfo()");

        int orderInfo = ThreadLocalRandom.current().nextInt(100);
        log.info("Mock info generated: {}", orderInfo);

        return svc.getUserInfo().doOnNext(userInfo -> log.info("Mock user info fetched: {}", userInfo))
                .map(userInfo -> orderInfo + userInfo);
    }
}
