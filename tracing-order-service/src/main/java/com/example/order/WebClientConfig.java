/*
 * Spring Boot Microservices tutorial - Tracing Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import java.util.List;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder(List<WebClientCustomizer> customizers) {
        WebClient.Builder builder = WebClient.builder();
        customizers.forEach(customizer -> customizer.customize(builder));
        return builder;
    }
}
