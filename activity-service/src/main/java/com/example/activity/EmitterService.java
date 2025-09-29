/*
 * Spring Boot Microservices tutorial - Activity Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmitterService {
    private static final Logger log = LogManager.getLogger(EmitterService.class);

    private final KafkaTemplate<String, ActivityEvent> template;

    @Value("${app.topic.activity-events}")
    private String topicName;

    public EmitterService(KafkaTemplate<String, ActivityEvent> template) {
        this.template = template;
    }

    public void emit(ActivityEvent event) {
        log.traceEntry("emit()");
        template.send(topicName, event.userId(), event);
        log.debug("Emitted {} to {}", event, topicName);
    }
}
