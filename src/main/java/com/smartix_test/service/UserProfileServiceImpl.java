package com.smartix_test.service;

import com.smartix_test.dto.UserProfileRequest;
import com.smartix_test.entity.UserProfile;
import com.smartix_test.exception.UserAccountNotFoundException;
import com.smartix_test.repo.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService{
    private final UserProfileRepo userProfileRepo;
    private final UserAccountServiceImpl userAccountServiceImpl;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseEntity<?> editUserProfile(UserProfileRequest request, String phoneNumber) {
        UserProfile entity;
        try {
            var userAccount = userAccountServiceImpl.getUserAccountByPhoneNumber(phoneNumber);
            entity = userAccount.getUserProfile();
        } catch (UserAccountNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (request.getFirstName() != null & !request.getFirstName().equals(entity.getFirstName()) ){
            entity.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null  & !request.getLastName().equals(entity.getLastName())){
            entity.setLastName(request.getLastName());
        }
        if (request.getMiddleName() != null  & !request.getMiddleName().equals(entity.getMiddleName())){
            entity.setMiddleName(request.getMiddleName());
        }
        if (request.getEmail() != null  & !request.getEmail().equals(entity.getEmail())){
            if (!UserAccountServiceImpl.isValidEmail(request.getEmail())){
                return ResponseEntity.badRequest().body("Invalid email address");
            } else entity.setEmail(request.getEmail());
        }
        if (request.getGender() != null  & request.getGender() != entity.getGender()){
            entity.setGender(request.getGender());
        }
        if (request.getDateOfBirth() != null & request.getDateOfBirth() != entity.getDateOfBirth()){
            entity.setDateOfBirth(request.getDateOfBirth());
        }
        userProfileRepo.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
