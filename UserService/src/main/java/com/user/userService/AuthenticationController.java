package com.user.userService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController implements IAuthenticationInterface {

    @Override
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void Authenticate() {
        //does nothing just auth interface
      
        
    }
    
}
