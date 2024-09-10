package com.smartix_test.service;

import com.smartix_test.dto.UserProfileRequest;
import org.springframework.http.ResponseEntity;

public interface UserProfileService {
    ResponseEntity<?> editUserProfile(UserProfileRequest request, String phoneNumber);
}
