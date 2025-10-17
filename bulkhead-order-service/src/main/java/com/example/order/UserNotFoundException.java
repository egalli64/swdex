/*
 * Spring Boot Microservices tutorial - Bulkhead Order Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User not found: " + userId);
    }

    public UserNotFoundException(Long userId, Throwable cause) {
        super("User not found: " + userId, cause);
    }
}
