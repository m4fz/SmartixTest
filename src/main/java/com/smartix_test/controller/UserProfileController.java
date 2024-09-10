package com.smartix_test.controller;

import com.smartix_test.dto.UserProfileRequest;
import com.smartix_test.service.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "Profile controller")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PatchMapping("/edit")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileRequest request, Authentication authentication) {
        return userProfileService.editUserProfile(request, authentication.getName());
    }
}
