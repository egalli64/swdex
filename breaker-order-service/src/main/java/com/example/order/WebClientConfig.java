/*
 * Spring Boot Microservices tutorial - Circuit Breaker Order Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import java.time.Duration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create() //
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000).responseTimeout(Duration.ofSeconds(3));

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
