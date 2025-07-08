package com.user.userService;


import org.springframework.web.bind.annotation.*;



@RestController
public interface IRegistrationInterface{

    /**
     * Get a list of users with optional sorting and pagination.
     *
     * @return a response string
     */
    @PostMapping("signIn")
    String SignInUser();

    @PostMapping("signUp")
    public String SignUpUser(@RequestParam String email, @RequestParam String password,
                              @RequestParam String firstName, @RequestParam String lastName);


    // Additional methods for creating, updating, and deleting users can be added here.
}
