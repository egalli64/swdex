/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

@SuppressWarnings("serial")
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found: " + id);
    }
}
