/*
 * Spring Boot Microservices tutorial - Audit Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.activity.ActivityEvent;

@Service
public class ActivityEventConsumer {

    private final List<ActivityEvent> events = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "activity.events", groupId = "audit-service-group")
    public void consume(ActivityEvent event) {
        events.add(event);
        System.out.println("Received event: " + event);
    }

    public List<ActivityEvent> getEventsByUser(String userId) {
        return events.stream().filter(e -> e.userId().equals(userId)).toList();
    }

    public List<ActivityEvent> getPurchaseEvents(String userId) {
        return events.stream().filter(e -> e.userId().equals(userId)).filter(e -> "PURCHASE".equals(e.type())).toList();
    }

    public List<ActivityEvent> getAllEvents() {
        return new ArrayList<>(events);
    }
}
