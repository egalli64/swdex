/*
 * Spring Boot Microservices tutorial - Tracing Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TracingOrderService {
    private static final String USER_SVC_URL = "http://tracing-user-service";
    private static final String REQUEST_URI = "/api/tracing-users";
    private static final Logger log = LogManager.getLogger(TracingOrderService.class);

    private final RestClient client;

    public TracingOrderService(RestClient.Builder builder) {
        this.client = builder.baseUrl(USER_SVC_URL).build();
    }

    public int getUserInfo() {
        log.traceEntry("getUserInfo()");

        try {
            return client.get().uri(REQUEST_URI).retrieve().body(Integer.class);
        } catch (Exception ex) {
            log.error("Can't get user info", ex);
            return 0;
        }
    }
}
