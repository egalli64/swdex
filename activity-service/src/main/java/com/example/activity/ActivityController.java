/*
 * Spring Boot Microservices tutorial - Activity Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.activity;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        curl -X POST http://localhost:8080/api/activities/test
     * </pre>
     */
    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestParam(defaultValue = "100") int count) {
        log.trace("test({})", count);

        String[] users = { "user1", "user2", "user3", "user4", "user5" };
        String[] types = { "VIEW", "CLICK", "PURCHASE", "LIKE", "SHARE" };
        String[] items = { "item-A", "item-B", "item-C", "item-D", "item-E" };

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < count; i++) {
            String userId = users[random.nextInt(users.length)];
            String type = types[random.nextInt(types.length)];
            String itemId = items[random.nextInt(items.length)];

            ActivityEvent event = new ActivityEvent(userId, type, itemId, Instant.now());
            emit(event);
        }

        return ResponseEntity.accepted().body("Generated " + count + " events");
    }
}
