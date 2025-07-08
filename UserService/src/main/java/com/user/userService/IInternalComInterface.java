package com.user.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public interface IInternalComInterface{

    /**
     * get a user.
     * 
     * requires token given to other internal services
     *
     * @return a response string
     */

    @GetMapping("/internal/user/{id}")
    String getUser(int id);

    /*
     * get all reviews for a vehicle
     * @params id - the id of the vehicle
     * 
     */
    @GetMapping("/internal/user/{id}/reviews")
    String getvehicleReviews(int id);

    

    // Additional methods for creating, updating, and deleting users can be added here.
}
