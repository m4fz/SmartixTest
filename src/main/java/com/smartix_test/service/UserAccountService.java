package com.smartix_test.service;

import com.smartix_test.dto.PaymentProcessResponse;
import com.smartix_test.dto.UserBalanceResponse;
import com.smartix_test.dto.UserPaymentProcessRequest;
import com.smartix_test.dto.UserRegisterRequest;
import com.smartix_test.entity.UserAccount;
import com.smartix_test.entity.UserPayment;
import com.smartix_test.entity.UserProfile;
import com.smartix_test.exception.UserAccountNotFoundException;
import com.smartix_test.repo.UserAccountRepo;
import com.smartix_test.repo.UserPaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepo userAccountRepo;
    private final PasswordEncoder passwordEncoder;
    public final UserPaymentRepo userPaymentRepo;

    public UserBalanceResponse getUserBalance(String phoneNubmer){
        var userAccount = getUserAccountByPhoneNumber(phoneNubmer);
        return UserBalanceResponse.builder().phoneNumber(phoneNubmer).balance(userAccount.getUserBalance()).build();
    }
    public ResponseEntity<?> proceedPayment(UserPaymentProcessRequest request, String phoneNumber){
        var userAccount = getUserAccountByPhoneNumber(phoneNumber);
        if (userAccount.getUserBalance().compareTo(request.getAmount()) < 0) {
            return new ResponseEntity<>("Not enough money", HttpStatus.BAD_REQUEST);
        }
        var finalBalance = userAccount.getUserBalance().subtract(request.getAmount());
        userAccount.setUserBalance(finalBalance);
        var userPayment = UserPayment.builder()
                .paymentAmount(request.getAmount())
                .paymentDate(LocalDateTime.now())
                .phoneNumber(phoneNumber)
                .account(userAccount)
                .build();
        var userPaymentSaved = userPaymentRepo.save(userPayment);

        List<UserPayment> userPayments = new ArrayList<>();
        userPayments.add(userPaymentSaved);
        userAccount.setUserPaymentList(userPayments);
        userAccountRepo.save(userAccount);
        return ResponseEntity.ok(PaymentProcessResponse.builder().phone(phoneNumber).message("Payment successful").build());
    }

    public ResponseEntity<?> saveAccount (UserRegisterRequest request){
        var entity = UserAccount.builder()
                .phoneNumber(request.getPhoneNumber())
                .userBalance(BigInteger.valueOf(1000L))
                .password(passwordEncoder.encode(request.getPassword()))
                .userProfile(UserProfile.builder()
                        .dateOfBirth(request.getUserProfileRequest().getDateOfBirth())
                        .email(request.getUserProfileRequest().getEmail())
                        .firstName(request.getUserProfileRequest().getFirstName())
                        .lastName(request.getUserProfileRequest().getLastName())
                        .middleName(request.getUserProfileRequest().getMiddleName())
                        .gender(request.getUserProfileRequest().getGender())
                        .build()).build();
        userAccountRepo.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public UserAccount getUserAccountByPhoneNumber(String phoneNumber){
        return userAccountRepo.findByPhoneNumber(phoneNumber).orElseThrow(()-> new UserAccountNotFoundException("User" + phoneNumber + "not found"));
    }

}
