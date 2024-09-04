package com.smartix_test.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public String getPhoneNumber(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var phoneNumber = "";
        if (authentication != null && authentication.isAuthenticated()) {
            phoneNumber = authentication.getName();
        }
        return phoneNumber;
    }
}
