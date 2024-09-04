package com.smartix_test.security;

import com.smartix_test.repo.UserAccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountDetailsService implements UserDetailsService {
    private final UserAccountRepo userAccountRepo;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        var userAccount = userAccountRepo.findByPhoneNumber(phoneNumber);
        if (userAccount.isPresent()){
            var userDetails = userAccount.get();
            return User.builder().username(userDetails.getPhoneNumber()).password(userDetails.getPassword()).roles("USER").build();
        } else {
            throw new UsernameNotFoundException("User " + phoneNumber + "not found");
        }
    }
}
