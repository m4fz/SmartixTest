package com.smartix_test.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PaymentProcessResponse {
    private String phone;
    private String message;
}
