package com.user.userService.services.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.kafka.EventConstants.CATALOGUE_EVENTS;
import static com.kafka.EventConstants.USER_ACTIVITY_EVENTS;


/**
 * Produces and publishes messages to Kafka Topics:
 *      - Catalogue Topic
 *      - Usage Topic
 */
@Service
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send a user activity event upon start of a user session
     * Used by:
     *      - AnalyticsService
     *
     * @param payload - UsageEvent OBJ
     */
    public void sendToUserActivityEvents(UsagePayload payload) {
        kafkaTemplate.send(USER_ACTIVITY_EVENTS, payload);
    }

    /**
     * Send a ReviewEvent upon a submitted review to update star rating
     * Used by:
     *      - CatalogueService
     *
     * @param payload - Integer OBJ
     */
    public void sendToCatalogueEvent(ReviewPayload payload) {
        kafkaTemplate.send(CATALOGUE_EVENTS, payload);
    }

}
