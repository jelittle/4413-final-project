package com.user.userService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.user.userService.dto.ReviewDTO;
import com.user.userService.models.ECommerceUser;
import com.user.userService.repositories.ECommerceUserRepository;
import com.user.userService.services.ReviewService;



@RestController
@RequestMapping("/internal")
public class InternalController implements IInternalComInterface{
    @Autowired
    private ECommerceUserRepository userRepository;
    @Autowired
    private ReviewService reviewService;

    /**
     * get a user.
     * 
     * requires token given to other internal services
     *
     * @return a response string
     */

    // @GetMapping("/")
    // String getUser(int id);

    /*
     * get all reviews for a vehicle
     * @params id - the id of the vehicle
     * 
     */
    // @GetMapping("/internal/reviews")
    // String getvehicleReviews(@RequestParam int id){


    // }
    @GetMapping("/user")
    public ECommerceUser getUserByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

  
    @GetMapping("/reviews")
    public List<ReviewDTO> getReviewsByVehicleId(@RequestParam String vehicleId) {
        return reviewService.getReviewsByVehicleId(vehicleId)
        .stream()
        .map(review -> new ReviewDTO(
            review.getUser().getUsername(),
            review.getVehicleId(),
            review.getReviewTitle(),
            review.getReviewBody(),
            review.getStarRating()
        ))
        .toList();



  }

  




}

    