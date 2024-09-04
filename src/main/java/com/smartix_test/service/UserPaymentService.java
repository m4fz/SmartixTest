package com.smartix_test.service;

import com.smartix_test.entity.UserAccount;
import com.smartix_test.exception.UserAccountNotFoundException;
import com.smartix_test.repo.UserPaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPaymentService {
    private final UserAccountService userAccountService;
    private final UserPaymentRepo userPaymentRepo;

    public ResponseEntity<?> getUserPayments(int page, int size, String phoneNumber){
        UserAccount userAccount;
        try {
            userAccount = userAccountService.getUserAccountByPhoneNumber(phoneNumber);
        } catch (UserAccountNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        var pageable = PageRequest.of(page,size);

        return ResponseEntity.ok(userPaymentRepo.findByAccount(userAccount.getUserId(), pageable).getContent());
    }

}
