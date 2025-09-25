/*
 * Spring Boot Microservices tutorial - Product service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static Logger log = LogManager.getLogger(ProductController.class);

    private final StreamBridge bridge;

    public ProductController(StreamBridge bridge) {
        this.bridge = bridge;
    }

    /**
     * <pre>
        curl -X POST http://localhost:8080/api/products ^
        -H "Content-Type: application/json" ^
        -d "{\"name\": \"XYZ 42\", \"description\": \"A powerful laptop for everyday use.\"}"
     * </pre>
     */
    @PostMapping
    public String create(@RequestBody Product product) {
        log.traceEntry("create({})", product);

        bridge.send("product-events", product);
        return "Product created and event sent to message queue.";
    }
}
