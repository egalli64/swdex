/*
 * Spring Boot Microservices tutorial - Tracing User Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracing-users")
public class TracingUserController {
    private static Logger log = LogManager.getLogger(TracingUserController.class);

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/tracing-users
     * </pre>
     */
    @GetMapping()
    public int getUserInfo() {
        log.traceEntry("getUserInfo()");
        
        int info = ThreadLocalRandom.current().nextInt(100);
        log.info("Mock info generated: " + info);
        
        return info;
    }
}
