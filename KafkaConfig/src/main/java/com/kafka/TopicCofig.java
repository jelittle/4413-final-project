package com.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


// Source for configurations: https://developer.confluent.io/courses/apache-kafka/brokers/

/**
 * Topic definition and configuration for Kafka
 */
@Configuration
public class TopicCofig {


    /**
     * Topic for activity recording
     *      - After a session is started, store:
     *      {user, time}
     *
     * Relation: Analytics
     */
    @Bean
    public NewTopic userActivityEventsTopic() {
        return TopicBuilder.name(EventConstants.USER_ACTIVITY_EVENTS)
                .partitions(3) // Higher partitionCount due to expected higher volume of messages
                .replicas(1)
                .build();
    }

    /**
     * Topic for transaction recording
     *      - After a transaction is performed, store:
     *       {user, vehicle, time, address}
     *
     * Relation: Analytics
     */
    @Bean
    public NewTopic transactionEventsTopic() {
        return TopicBuilder.name(EventConstants.TRANSACTION_EVENTS)
                .partitions(1)
                .replicas(1)
                .build();
    }


    /**
     * Topic for catalogue updates
     *      - After a purchase is made
     *      - After a review is published by a user
     *
     */
    @Bean
    public NewTopic catalogueEventsTopic() {
        return TopicBuilder.name(EventConstants.CATALOGUE_EVENTS)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
