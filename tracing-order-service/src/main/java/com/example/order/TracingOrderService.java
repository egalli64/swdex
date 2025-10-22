/*
 * Spring Boot Microservices tutorial - Tracing Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class TracingOrderService {
    private static final String USER_SVC_URL = "http://tracing-user-service";
    private static final String REQUEST_URI = "/api/tracing-users";
    private static final Logger log = LogManager.getLogger(TracingOrderService.class);

    private final WebClient client;

    public TracingOrderService(WebClient.Builder builder) {
        this.client = builder.baseUrl(USER_SVC_URL).build();
    }

    public Mono<Integer> getUserInfo() {
        log.trace("Enter getUserInfo()");

        return client.get().uri(REQUEST_URI).retrieve().bodyToMono(Integer.class)
                .doOnError(ex -> log.error("Can't get user info", ex)).onErrorReturn(0);
    }
}
