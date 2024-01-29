package org.example.youth_be.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.youth_be.user.controller.spec.UserSpec;
import org.example.youth_be.user.service.UserService;
import org.example.youth_be.user.service.request.DevUserProfileCreateRequest;
import org.example.youth_be.user.service.request.UserProfileUpdateRequest;
import org.example.youth_be.user.service.response.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpec {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUserForDev(@RequestBody DevUserProfileCreateRequest request) {
        return userService.createUserForDev(request);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse getUserProfile(@PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserProfile(@PathVariable Long userId, @RequestBody UserProfileUpdateRequest request) {
        userService.updateUserProfile(userId, request);
    }
}
