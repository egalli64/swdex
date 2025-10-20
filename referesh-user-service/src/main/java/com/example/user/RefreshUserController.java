/*
 * Spring Boot Microservices tutorial - Refresh User Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/refresh-users")
public class RefreshUserController {
    private static Logger log = LogManager.getLogger(RefreshUserController.class);

    /**
     * To refresh the property post on the microservice port:
     * 
     * <pre>
        curl -X POST http://localhost:8281/actuator/refresh
     * </pre>
     */
    @Value("${user.property}")
    private String property;

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/refresh-users/property
     * </pre>
     */
    @GetMapping("/property")
    public String getUserProperty() {
        log.traceEntry("getUserProperty()");
        return property;
    }
}
