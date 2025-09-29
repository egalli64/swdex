/*
 * Spring Boot Microservices tutorial - Activity Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.activity;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${app.topic.activity-events}")
    private String topicName;
    
    @Bean
    NewTopic activityEventsTopic() {
        return TopicBuilder.name(topicName).partitions(3).replicas(1).build();
    }
}
