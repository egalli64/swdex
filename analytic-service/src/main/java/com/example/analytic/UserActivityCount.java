/*
 * Spring Boot Microservices tutorial - Analytic Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.analytic;

import java.time.Instant;

public record UserActivityCount(String userId, Instant start, Instant end, long count) {
}
