package com.smartix_test.service;

import com.smartix_test.dto.UserBalanceResponse;
import com.smartix_test.dto.UserPaymentProcessRequest;
import com.smartix_test.dto.UserRegisterRequest;
import com.smartix_test.entity.UserAccount;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {
    UserBalanceResponse getUserBalance(String phoneNubmer);
    ResponseEntity<?> proceedPayment(UserPaymentProcessRequest request, String phoneNumber);
    ResponseEntity<?> saveAccount (UserRegisterRequest request);
    UserAccount getUserAccountByPhoneNumber(String phoneNumber);
}
