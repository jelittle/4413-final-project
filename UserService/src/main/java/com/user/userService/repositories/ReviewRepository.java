package com.user.userService.repositories;

import com.user.userService.models.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByVehicleId(String vehicleId);
}