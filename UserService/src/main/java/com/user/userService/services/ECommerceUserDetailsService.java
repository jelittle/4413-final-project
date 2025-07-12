package com.user.userService.services;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.userService.models.ECommerceUser;
import com.user.userService.models.Review;
import com.user.userService.repositories.ECommerceUserRepository;
import com.user.userService.repositories.ReviewRepository;


@Service
public class ECommerceUserDetailsService implements UserDetailsService {

    private final ECommerceUserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public ECommerceUserDetailsService(ECommerceUserRepository userRepository, PasswordEncoder passwordEncoder,ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reviewRepository= reviewRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public void registerUser(String username, String password, String email, String first_name, String last_name) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + username);
        }

        ECommerceUser user = new ECommerceUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Use injected encoder
        user.setEmail(email);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setEnabled(true);
        user.setAuthorities(Set.of("ROLE_USER")); // Set default role

        userRepository.save(user);
    }

    public void addReview(ECommerceUser user, String vehicleId, String reviewTitle, String reviewBody, Float starRating) {
        Review review = new Review();



        review.setUser(user);
        review.setVehicleId(vehicleId);
        review.setReviewTitle(reviewTitle);
        review.setReviewBody(reviewBody);
        review.setStarRating(starRating);


        reviewRepository.save(review);
    }
}

