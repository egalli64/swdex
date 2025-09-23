/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        log.traceEntry("handleUserNotFound()");

        Map<String, Object> error = Map.of( //
                "error", "Not Found", //
                "message", ex.getMessage(), //
                "timestamp", LocalDateTime.now());
        return Mono.just(error);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Map<String, Object>> handleServiceUnavailable(ServiceUnavailableException ex) {
        log.traceEntry("handleServiceUnavailable()");

        Map<String, Object> error = Map.of( //
                "error", "Service Not Available", //
                "message", ex.getMessage(), //
                "timestamp", LocalDateTime.now());
        return Mono.just(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, Object>> handleGeneral(Exception ex) {
        Map<String, Object> error = Map.of( //
                "error", "Internal Server Error", //
                "message", "An unexpected error occurred", //
                "timestamp", LocalDateTime.now());
        return Mono.just(error);
    }
}
