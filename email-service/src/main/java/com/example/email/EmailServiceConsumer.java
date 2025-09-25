/*
 * Spring Boot Microservices tutorial - Email service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class EmailServiceConsumer {
    private static final Logger log = LogManager.getLogger(EmailServiceConsumer.class);

    @Bean
    Consumer<Product> newProductEvent() {
        return product -> {
            log.info("Received new product event: {}", product);

            sendEmailNotification(product);
        };
    }

    private void sendEmailNotification(Product product) {
        log.info("Sending email notification for new product: '{}'", product.name());
        log.info("Email content: A new product, '{}', has been added. Check it out!", product.name());
    }
}
