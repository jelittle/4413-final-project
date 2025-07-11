package com.user.userService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews", indexes = @Index(name = "idx_vehicle_id", columnList = "vehicle_id"))
public class Review {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private ECommerceUser user; 
    @Column(name = "vehicle_id", nullable = false)
    private String vehicleId;  

    private String reviewTitle;
    private String reviewBody;
    private float starRating;

    public Review() {

    }

    public Review(ECommerceUser user, String vehicleId, String reviewTitle, String reviewBody, Integer starRating) {
        this.user = user;
        this.vehicleId = vehicleId;
        this.reviewTitle = reviewTitle;
        this.reviewBody = reviewBody;
        this.starRating = starRating;
    }

    public ECommerceUser getUser() {
        return user;
    }

    public void setUser(ECommerceUser user) {
        this.user = user;
    }
    public String getUsername() {
        return user.getUsername();
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
