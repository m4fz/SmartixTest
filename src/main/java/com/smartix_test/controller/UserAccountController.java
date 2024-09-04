package com.smartix_test.controller;

import com.smartix_test.dto.UserPaymentProcessRequest;
import com.smartix_test.security.SecurityUtil;
import com.smartix_test.service.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-account")
@RequiredArgsConstructor
@Tag(name = "Account Controller")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final SecurityUtil securityUtil;

//    @Tag(name = "Get Balance")
    @GetMapping("/get-balance")
    public ResponseEntity<?> getBalance(){
        return ResponseEntity.ok(userAccountService.getUserBalance(securityUtil.getPhoneNumber()));
    }
//    @Tag(name = "Make payment")
    @PostMapping("/payment-proceed")
    public ResponseEntity<?> paymentProceed(@Valid @RequestBody UserPaymentProcessRequest request){
        return userAccountService.proceedPayment(request,securityUtil.getPhoneNumber());
    }
}
