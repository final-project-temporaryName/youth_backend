package org.example.youth_be.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.youth_be.user.service.request.LoginRequest;
import org.example.youth_be.user.service.response.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAuthController {
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

    }
}