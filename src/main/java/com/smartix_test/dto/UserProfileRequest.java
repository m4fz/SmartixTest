package com.smartix_test.dto;

import com.smartix_test.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private Gender gender;
    private LocalDate dateOfBirth;
}
