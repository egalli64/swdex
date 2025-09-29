/*
 * Spring Boot Microservices tutorial - Activity Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.activity;

import java.time.Instant;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Check the topic status (order key is userId):
 * 
 * <pre>
    docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic activity.events --from-beginning
 * </pre>
 * 
 * Could also be useful: --max-messages 10
 */
@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private static final Logger log = LogManager.getLogger(ActivityController.class);
    private final EmitterService svc;

    public ActivityController(EmitterService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
        curl -X POST http://localhost:8080/api/activities/record -H "Content-Type: application/json" ^
         -d "{ \"userId\": \"user-B\", \"type\": \"LOGIN\", \"itemId\": \"app-entry\"}"
     * </pre>
     */
    @PostMapping("/record")
    public ResponseEntity<String> emit(@RequestBody ActivityEvent event) {
        log.trace("emit({})", event);

        svc.emit(event);
        return ResponseEntity.accepted().body("Event emitted successfully");
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/activities/mocker
     * </pre>
     */
    @GetMapping("/mocker")
    public ResponseEntity<String> mockMulti() {
        log.trace("mockMulti({})");

        List.of(new ActivityEvent("user-A", "PAGE_VIEW", "home", Instant.now()),
                new ActivityEvent("user-B", "ADD_TO_CART", "sku-888", Instant.now().plusSeconds(1)),
                new ActivityEvent("user-A", "PRODUCT_VIEW", "sku-456", Instant.now().plusSeconds(2)),
                new ActivityEvent("user-C", "CHECKOUT", "cart-99", Instant.now().plusSeconds(3)) //
        ).forEach(svc::emit);

        return ResponseEntity.ok().body("Mock events emitted");
    }
}
