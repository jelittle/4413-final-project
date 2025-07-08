package com.user.userService.services;

import com.user.userService.models.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.user.userService.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class UserServiceDOA implements IUserInterface {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user with the provided details.
     *@param email The email address
     * @param hashedPass The hashed password
     * @param ...
     */
    public String CreateUser(String email, String hashedPass, String firstName, String lastName){


        User user = new User();
        user.setEmail(email);
        user.setHashedPass(BCrypt.hashpw(hashedPass, BCrypt.gensalt()));
        user.setFirstName(firstName);
        user.setLastName(lastName);


        userRepository.save(user);
        return "success";
        
    }
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getHashedPass())) {
            return "User signed in successfully";
        } else {
            return "Invalid email or password";
        }
    }

}
