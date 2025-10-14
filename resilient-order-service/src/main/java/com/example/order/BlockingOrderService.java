/*
 * Spring Boot Microservices tutorial - Resilient order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class BlockingOrderService {
    private static final Logger log = LogManager.getLogger(BlockingOrderService.class);
    private static final String GET_USER_BY_ID = "http://user-service/api/users/{id}";

    private final RestClient rest;

    public BlockingOrderService(RestClient rest) {
        this.rest = rest;
    }

    @Retry(name = "userService")
    public UserDTO getUser(Long id) {
        log.traceEntry("getUser({})", id);

        return rest.get().uri(GET_USER_BY_ID, id).retrieve().body(UserDTO.class);
    }
}
