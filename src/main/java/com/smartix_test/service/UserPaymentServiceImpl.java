package com.smartix_test.service;

import com.smartix_test.entity.UserAccount;
import com.smartix_test.exception.UserAccountNotFoundException;
import com.smartix_test.repo.UserPaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPaymentServiceImpl implements UserPaymentService{
    private final UserAccountServiceImpl userAccountServiceImpl;
    private final UserPaymentRepo userPaymentRepo;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserPayments(int page, int size, String phoneNumber){
        UserAccount userAccount;
        try {
            userAccount = userAccountServiceImpl.getUserAccountByPhoneNumber(phoneNumber);
        } catch (UserAccountNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        var pageable = PageRequest.of(page,size);

        return ResponseEntity.ok(userPaymentRepo.findByAccount(userAccount.getUserId(), pageable).getContent());
    }

}
