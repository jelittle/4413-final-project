package com.user.userService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long userId;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    private String hashedPass;
    private String salt;
    private String FirstName;
    private String LastName;
    private String PhoneNum;

    public User() {

    }

    public User(Long userId, String email, String hashedPass, String salt, String firstName, String lastName, String phoneNum) {
        this.userId = userId;
        this.email = email;
        this.hashedPass = hashedPass;
        this.salt = salt;
        FirstName = firstName;
        LastName = lastName;
        PhoneNum = phoneNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }
}
