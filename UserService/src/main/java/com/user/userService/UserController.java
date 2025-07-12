package com.user.userService;

import com.user.userService.models.ECommerceUser;
import com.user.userService.services.ECommerceUserDetailsService;

import com.user.userService.services.kafka.EventProducer;
import com.user.userService.services.kafka.ReviewPayload;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    // @Autowired
    // private ECommerceUserRepository userRepository;

    @Autowired
    private ECommerceUserDetailsService userService;


    // ----- KAFKA STUFF -----
    private final EventProducer eventProducer;

    @Autowired
    public UserController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }
    // ----- KAFKA DONE -----

    @GetMapping("/me")
    public ECommerceUser getCurrentUser(@AuthenticationPrincipal ECommerceUser user) {
        return user;
    }

   


    @PostMapping("/addReview")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addReview(@AuthenticationPrincipal ECommerceUser user, 
                            @RequestParam String vehicleId,
                            @RequestParam String reviewTitle,
                            @RequestParam String reviewBody,
                            @RequestParam Float starRating){
        userService.addReview(user, vehicleId, reviewTitle, reviewBody, starRating);

        // SENDING KAFKA REVIEW UPDATE
        eventProducer.sendToCatalogueEvent(new ReviewPayload(starRating.intValue()));

    }
    


}