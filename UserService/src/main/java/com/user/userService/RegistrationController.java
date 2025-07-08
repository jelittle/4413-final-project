package com.user.userService;


import org.springframework.beans.factory.annotation.Autowired;
// import com.user.userService.models.User;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.server.ResponseStatusException;

import com.user.userService.services.UserServiceDOA;

import java.util.List;


@RestController
@RequestMapping("/register")
public class RegistrationController implements IRegistrationInterface {

    @Autowired
    private UserServiceDOA userService;
    /**
     * Get a list of users with optional sorting and pagination.
     *
     * @return a response string
     */
    @PostMapping("/signIn")
    public String SignInUser() {

        // Logic for signing in a user goes here
      
        return "User signed in successfully";
    }

    @PostMapping("/signUp")
    public String SignUpUser(@RequestParam String email, @RequestParam String password,
                              @RequestParam String firstName, @RequestParam String lastName){

        return userService.CreateUser(email, password, firstName, lastName);           


    }


    // Additional methods for creating, updating, and deleting users can be added here.
}
