/*
 * Spring Boot Microservices tutorial - API Gateway
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.gateway;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class DebugController {
    private final DiscoveryClient discoveryClient;
    private final RouteLocator routeLocator;

    public DebugController(DiscoveryClient discoveryClient, RouteLocator routeLocator) {
        this.discoveryClient = discoveryClient;
        this.routeLocator = routeLocator;
    }

    @GetMapping("/routes")
    public Flux<Route> getRoutes() {
        return routeLocator.getRoutes();
    }

    @GetMapping("/services")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    @GetMapping("/instances/{service}")
    public List<ServiceInstance> getInstances(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }

    @GetMapping("/test")
    public String test() {
        return "Gateway is working!";
    }
}
