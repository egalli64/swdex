/*
 * Spring Boot Microservices tutorial - Inventory Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class InventoryServiceConsumer {
    private static final Logger log = LogManager.getLogger(InventoryServiceConsumer.class);

    @Bean
    Consumer<Product> newProductEvent() {
        return product -> {
            log.info("Received new product event: {}", product);

            adjustStock(product);
        };
    }

    private void adjustStock(Product product) {
        log.info("Adjusting stock for product: {}", product.name());
    }
}
