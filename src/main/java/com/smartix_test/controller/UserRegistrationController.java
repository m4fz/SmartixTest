package com.smartix_test.controller;

import com.smartix_test.dto.UserRegisterRequest;
import com.smartix_test.service.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Registration controller")
public class UserRegistrationController {
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest request){
        return userAccountService.saveAccount(request);
    }
}
