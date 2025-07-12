package com.user.userService;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.user.userService.dto.ReviewDTO;
import com.user.userService.models.ECommerceUser;



@RestController
public interface IInternalComInterface{


    ECommerceUser getUserByUsername(@RequestParam String username);

    List<ReviewDTO> getReviewsByVehicleId(@RequestParam String vehicleId);

    



}

    
