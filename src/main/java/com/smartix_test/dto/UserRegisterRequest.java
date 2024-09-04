package com.smartix_test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotNull(message = "phoneNumber not null")
    public String phoneNumber;
    @NotNull(message = "password not null, or doesn't fit length from 4 to 32 char's")
    @Size(min = 4, max = 32)
    public String password;
    @NotNull(message = "userProfile not null")
    public UserProfileRequest userProfileRequest;
}
