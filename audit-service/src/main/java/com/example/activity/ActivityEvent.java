/*
 * Spring Boot Microservices tutorial - Audit Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.activity;

import java.time.Instant;

public record ActivityEvent(String userId, String type, String itemId, Instant timestamp) {
    public ActivityEvent {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}
