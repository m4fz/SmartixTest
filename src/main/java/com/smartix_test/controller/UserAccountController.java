package com.smartix_test.controller;

import com.smartix_test.dto.UserPaymentProcessRequest;
import com.smartix_test.service.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-account")
@RequiredArgsConstructor
@Tag(name = "Account Controller")
public class UserAccountController {
    private final UserAccountService userAccountService;

//    @Tag(name = "Get Balance")
    @GetMapping("/get-balance")
    public ResponseEntity<?> getBalance(Authentication authentication){
        return ResponseEntity.ok(userAccountService.getUserBalance(authentication.getName()));
    }
//    @Tag(name = "Make payment")
    @PostMapping("/payment-proceed")
    public ResponseEntity<?> paymentProceed(Authentication authentication, @RequestBody UserPaymentProcessRequest request){
        return userAccountService.proceedPayment(request,authentication.getName());
    }
}
