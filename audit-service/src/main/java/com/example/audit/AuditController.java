/*
 * Spring Boot Microservices tutorial - Audit Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.audit;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.activity.ActivityEvent;

@RestController
@RequestMapping("/api/audits")
public class AuditController {

    private final ActivityEventConsumer consumer;

    public AuditController(ActivityEventConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/audits/events
     * </pre>
     */
    @GetMapping("/events")
    public List<ActivityEvent> getAllEvents() {
        return consumer.getAllEvents();
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/audits/users/user-1/events
     * </pre>
     */
    @GetMapping("/users/{userId}/events")
    public List<ActivityEvent> getUserEvents(@PathVariable String userId) {
        return consumer.getEventsByUser(userId);
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/audits/users/user-1/purchases
     * </pre>
     */
    @GetMapping("/users/{userId}/purchases")
    public List<ActivityEvent> getUserPurchases(@PathVariable String userId) {
        return consumer.getPurchaseEvents(userId);
    }
}