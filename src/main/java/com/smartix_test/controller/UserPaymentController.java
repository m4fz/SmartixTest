package com.smartix_test.controller;

import com.smartix_test.security.SecurityUtil;
import com.smartix_test.service.UserPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-controller")
@RequiredArgsConstructor
@Tag(name = "Payment controller")
public class UserPaymentController {
    private final UserPaymentService userPaymentService;
    private final SecurityUtil securityUtil;

    @GetMapping("/get-list")
    public ResponseEntity<?> getPaymentList(@RequestParam int page, @RequestParam int size){
        return userPaymentService.getUserPayments(page,size, securityUtil.getPhoneNumber());
    }
}
