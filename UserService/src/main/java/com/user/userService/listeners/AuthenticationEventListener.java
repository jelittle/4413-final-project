package com.user.userService.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationEventListener implements ApplicationListener<org.springframework.context.ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent success) {
            System.out.println("yay"+success);
        }
    }
}