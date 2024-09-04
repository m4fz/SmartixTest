package com.smartix_test.service;

import com.smartix_test.dto.UserProfileRequest;
import com.smartix_test.entity.UserProfile;
import com.smartix_test.exception.UserAccountNotFoundException;
import com.smartix_test.repo.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepo userProfileRepo;
    private final UserAccountService userAccountService;

    public ResponseEntity<?> editUserProfile(UserProfileRequest request, String phoneNumber) {
        UserProfile entity;
        try {
            var userAccount = userAccountService.getUserAccountByPhoneNumber(phoneNumber);
            entity = userAccount.getUserProfile();
        } catch (UserAccountNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (request.getDateOfBirth() != null ){
            entity.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getFirstName() != null ){
            entity.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null ){
            entity.setLastName(request.getLastName());
        }
        if (request.getMiddleName() != null ){
            entity.setMiddleName(request.getMiddleName());
        }
        if (request.getGender() != null ){
            entity.setGender(request.getGender());
        }
        userProfileRepo.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
