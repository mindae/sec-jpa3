package com.mindae.secjpa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @GetMapping("/")
    public String getHome() {
        return "home3";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String getUser() {
        return "user3 page";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    String getAdmin() {
        return "admin3 page";
    }
}
