package com.example.simpleweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<String> info (Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body("login user: " + username);
    }
}
