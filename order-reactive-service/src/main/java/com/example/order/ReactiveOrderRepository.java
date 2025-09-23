/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveOrderRepository extends ReactiveCrudRepository<ReactiveOrder, Long> {
}