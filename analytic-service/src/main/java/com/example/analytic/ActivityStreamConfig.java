/*
 * Spring Boot Microservices tutorial - Analytic Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.analytic;

import java.time.Duration;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

/**
 * To check the count topic
 * 
 * <pre>
    docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic activity.counts --from-beginning
 * </pre>
 * 
 * Could also be useful: --max-messages 10
 */
@Configuration
@EnableKafkaStreams
public class ActivityStreamConfig {
    @Bean
    StreamsBuilderFactoryBeanConfigurer configurer() {
        return fb -> fb.setStateListener((newState, oldState) -> {
            System.out.println("State transition from " + oldState + " to " + newState);
        });
    }

    @Bean
    KStream<String, ActivityEvent> activityStream(StreamsBuilder builder) {
        KStream<String, ActivityEvent> stream = builder.stream("activity.events");

        stream.groupByKey().windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(5))) //
                .count(Materialized.as("user-activity-counts")) //
                .toStream()
                .map((win, count) -> KeyValue.pair(win.key(),
                        new UserActivityCount(win.key(), win.window().startTime(), win.window().endTime(), count)))
                .to("activity.counts");

        return stream;
    }
}
