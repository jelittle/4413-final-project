package com.user.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public interface IAuthenticationInterface {

    /**
     * Get a list of users with optional sorting and pagination.
     *
     * @return a response string
     */

    @GetMapping("/Authenticate/")
    String Authenticate();
    

    // Additional methods for creating, updating, and deleting users can be added here.
}
