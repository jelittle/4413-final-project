package com.user.userService.models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;

//@Entity
public class Review {
//    @Id
    private Long userId;        // PK
//    @Id
    private String vehicleId;   // PK
    private String reviewTitle;
    private String reviewBody;
    private Integer starRating;

    public Review() {

    }

    public Review(Long userId, String vehicleId, String reviewTitle, String reviewBody, Integer starRating) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.reviewTitle = reviewTitle;
        this.reviewBody = reviewBody;
        this.starRating = starRating;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }
}
