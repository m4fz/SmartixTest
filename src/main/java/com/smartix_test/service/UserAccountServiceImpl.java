package com.smartix_test.service;

import com.smartix_test.dto.PaymentProcessResponse;
import com.smartix_test.dto.UserBalanceResponse;
import com.smartix_test.dto.UserPaymentProcessRequest;
import com.smartix_test.dto.UserRegisterRequest;
import com.smartix_test.entity.UserAccount;
import com.smartix_test.entity.UserPayment;
import com.smartix_test.entity.UserProfile;
import com.smartix_test.exception.UserAccountNotFoundException;
import com.smartix_test.repo.UserAccountRepo;
import com.smartix_test.repo.UserPaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService{
    private final UserAccountRepo userAccountRepo;
    private final PasswordEncoder passwordEncoder;
    public final UserPaymentRepo userPaymentRepo;
    public static final Pattern VALID_EMAIL_ADDRESS =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    @Transactional(readOnly = true)

    public UserBalanceResponse getUserBalance(String phoneNubmer){
        var userAccount = getUserAccountByPhoneNumber(phoneNubmer);
        return UserBalanceResponse.builder().phoneNumber(phoneNubmer).balance(userAccount.getUserBalance()).build();
    }
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseEntity<?> proceedPayment(UserPaymentProcessRequest request, String phoneNumber){
        if (request.getAmount().equals(BigInteger.ZERO) || request.getAmount().compareTo(BigInteger.ZERO) < 0){
            return ResponseEntity.badRequest().body("Invalid amount, must be > 0");
        }
//        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()){
//            phoneNumber = request.getPhoneNumber();
//        }
        var userAccount = getUserAccountByPhoneNumber(phoneNumber);
        if (userAccount.getUserBalance().compareTo(request.getAmount()) < 0) {
            return new ResponseEntity<>("Not enough money", HttpStatus.BAD_REQUEST);
        }
        var finalBalance = userAccount.getUserBalance().subtract(request.getAmount());
        userAccount.setUserBalance(finalBalance);
        var userPayment = UserPayment.builder()
                .paymentAmount(request.getAmount())
                .paymentDate(LocalDateTime.now())
                .phoneNumber(phoneNumber)
                .account(userAccount)
                .build();
        var userPaymentSaved = userPaymentRepo.save(userPayment);

        List<UserPayment> userPayments = new ArrayList<>();
        userPayments.add(userPaymentSaved);
        userAccount.setUserPaymentList(userPayments);
        userAccountRepo.save(userAccount);
        return ResponseEntity.ok(PaymentProcessResponse.builder().phone(phoneNumber).message("Payment successful").build());
    }
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseEntity<?> saveAccount (UserRegisterRequest request){
        if (request.getPassword() == null || request.getPassword().isEmpty() || request.getPassword().length() < 4 || request.getPassword().length() > 32){
            return ResponseEntity.badRequest().body("Invalid password, it must not be empty and must be > 4 and < 32 char's");
        }
        if (!isValidEmail(request.getUserProfileRequest().getEmail())){
            return ResponseEntity.badRequest().body("Invalid email address");
        }
        var user = userAccountRepo.findByPhoneNumber(request.getPhoneNumber());

        if (user.isPresent() ){
            if (user.get().getUserProfile().getEmail().equals(request.getUserProfileRequest().getEmail())){
                return ResponseEntity.badRequest().body("User with " + request.getUserProfileRequest().getEmail() + " email already exists");
            }

            return ResponseEntity.badRequest().body("User already exists");
        }
        var entity = UserAccount.builder()
                .phoneNumber(request.getPhoneNumber())
                .userBalance(BigInteger.valueOf(1000L))
                .password(passwordEncoder.encode(request.getPassword()))
                .userProfile(UserProfile.builder()
                        .dateOfBirth(request.getUserProfileRequest().getDateOfBirth())
                        .email(request.getUserProfileRequest().getEmail())
                        .firstName(request.getUserProfileRequest().getFirstName())
                        .lastName(request.getUserProfileRequest().getLastName())
                        .middleName(request.getUserProfileRequest().getMiddleName())
                        .gender(request.getUserProfileRequest().getGender())
                        .build()).build();
        userAccountRepo.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Override
    public UserAccount getUserAccountByPhoneNumber(String phoneNumber){
        return userAccountRepo.findByPhoneNumber(phoneNumber).orElseThrow(()-> new UserAccountNotFoundException("User" + phoneNumber + "not found"));
    }

    public static boolean isValidEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }

}
