package com.ctrlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String getWelcomeMessage() {
        return "Welcome to Spring Security";
    }

    @GetMapping("/user")
    public Principal getUser(Principal principal) {
        return principal;
    }
}
