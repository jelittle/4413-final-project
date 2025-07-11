package com.user.userService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.user.userService.services.ECommerceUserDetailsService;



@RestController
@RequestMapping("/register")
public class RegistrationController implements IRegistrationInterface {

    @Autowired
    private ECommerceUserDetailsService userService;




    @PostMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void SignUpUser(@RequestParam String username, @RequestParam String email, @RequestParam String password,
                           @RequestParam String firstName, @RequestParam String lastName) {
        
        userService.registerUser(username, password, email, firstName, lastName);
    }


    }



