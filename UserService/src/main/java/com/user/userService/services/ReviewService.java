package com.user.userService.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.userService.models.Review;
import com.user.userService.repositories.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;


    public List<Review> getReviewsByVehicleId(String vehicleId) {
       
        return reviewRepository.findAllByVehicleId(vehicleId);
    }

    

}
