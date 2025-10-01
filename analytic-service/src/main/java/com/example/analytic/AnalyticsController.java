/*
 * Spring Boot Microservices tutorial - Analytic Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.analytic;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final StreamsBuilderFactoryBean factoryBean;

    public AnalyticsController(StreamsBuilderFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/analytics/users/user-1/activity
     * </pre>
     */
    @GetMapping("/users/{userId}/activity")
    public List<UserActivityCount> getUserActivity(@PathVariable String userId) {
        KafkaStreams streams = factoryBean.getKafkaStreams();
        ReadOnlyWindowStore<String, Long> store = streams
                .store(StoreQueryParameters.fromNameAndType("user-activity-counts", QueryableStoreTypes.windowStore()));

        List<UserActivityCount> results = new ArrayList<>();
        Instant now = Instant.now();

        try (WindowStoreIterator<Long> iterator = store.fetch(userId, now.minus(Duration.ofMinutes(30)), now)) {
            while (iterator.hasNext()) {
                KeyValue<Long, Long> next = iterator.next();
                Instant start = Instant.ofEpochMilli(next.key);
                Instant end = start.plus(Duration.ofMinutes(5));
                results.add(new UserActivityCount(userId, start, end, next.value));
            }
        }

        return results;
    }
}
