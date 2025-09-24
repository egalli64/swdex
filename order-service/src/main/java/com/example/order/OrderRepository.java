/*
 * Spring Boot Microservices tutorial - Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
