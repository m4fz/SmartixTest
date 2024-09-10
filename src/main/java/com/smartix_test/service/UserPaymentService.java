package com.smartix_test.service;

import org.springframework.http.ResponseEntity;

public interface UserPaymentService {
    ResponseEntity<?> getUserPayments(int page, int size, String phoneNumber);
}
