package com.controller;

import com.dao.Greeting;
import com.dao.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class UserController {

    private AtomicInteger counter = new AtomicInteger();

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Greeting getUser(@AuthenticationPrincipal User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        user = (com.dao.user.User) authentication.getPrincipal();
        if (user == null)
            return new Greeting(counter.incrementAndGet(), null);
        return new Greeting(counter.incrementAndGet(), user.getName());
    }
}
