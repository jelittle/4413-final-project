package com.user.userService;


import org.springframework.web.bind.annotation.*;



@RestController
public interface IRegistrationInterface{


   public void SignUpUser(@RequestParam String username,@RequestParam String email, @RequestParam String password,
                              @RequestParam String firstName, @RequestParam String lastName);


    // Additional methods for creating, updating, and deleting users can be added here.
}
