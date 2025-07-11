package com.user.userService.dto;

public class ReviewDTO {
    private String username;
    private String vehicleId;
    private String reviewTitle;
    private String reviewBody;
    private float starRating;

    // constructor
    public ReviewDTO(String username, String vehicleId, String reviewTitle, String reviewBody, float starRating) {
        this.username = username;
        this.vehicleId = vehicleId;
        this.reviewTitle = reviewTitle;
        this.reviewBody = reviewBody;
        this.starRating = starRating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(float starRating) {
        this.starRating = starRating;
    }


    
}
