/*
 * Spring Boot Microservices tutorial - Random service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.random;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/random")
public class RandomController {
    private static Logger log = LogManager.getLogger(RandomController.class);

    @Value("${spring.application.name}")
    private String name;

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/random
     * </pre>
     */
    @GetMapping
    public Map<String, String> get() {
        log.traceEntry("Get for {}", name);
        return Map.of("key", name, "id", UUID.randomUUID().toString(), "timestamp", Instant.now().toString());
    }
}
