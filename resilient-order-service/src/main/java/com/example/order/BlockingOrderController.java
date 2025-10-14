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
import reactor.core.scheduler.Schedulers;

/**
 * Using a RestClient in a resilient way
 */
@RestController
@RequestMapping("/api/resilient-orders/blocking")
public class BlockingOrderController {
    private static Logger log = LogManager.getLogger(BlockingOrderController.class);

    private final BlockingOrderService svc;

    public BlockingOrderController(BlockingOrderService svc) {
        this.svc = svc;
    }

    /**
     * Since this service runs on Netty, we need to tweak the code to use a blocking
     * service on an inherently reactive architecture
     * <p>
     * !!! Use this approach only if you can't avoid it !!!
     * 
     * <pre>
        curl -i -X GET http://localhost:8080/api/resilient-orders/blocking/1/user
     * </pre>
     */
    @GetMapping("/{id}/user")
    public Mono<UserDTO> getUser(@PathVariable Long id) {
        log.traceEntry("getOrderUser({})", id);
        return Mono.fromCallable(() -> svc.getUser(id)).subscribeOn(Schedulers.boundedElastic());
    }
}
