package com.user.userService.services;

public interface IUserInterface {
    /**
     * Creates a new user with the provided details.
     *@param email The email address
     * @param hashedPass The hashed password
     * @param ...
     */
    String CreateUser(String email, String hashedPass, String firstName, String lastName);

}