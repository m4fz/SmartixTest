package com.smartix_test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigInteger;

@Data
public class UserPaymentProcessRequest {
    @NotNull(message = "Payment amount shouldn't be empty")
    @Positive(message = "Payment amount should be positive")
    private BigInteger amount;
}
