package com.user.userService.services.kafka;

/**
 * POJO for Kafka Producer
 */
public class ReviewPayload {

    private Integer rating;

    public ReviewPayload() {}

    public ReviewPayload(Integer rating) {
        this.rating = rating;
    }


    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
