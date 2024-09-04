package com.smartix_test.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class UserBalanceResponse {
    private String phoneNumber;
    private BigInteger balance;
}
