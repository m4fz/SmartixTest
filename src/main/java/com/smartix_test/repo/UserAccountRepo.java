package com.smartix_test.repo;

import com.smartix_test.entity.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByPhoneNumber(String phoneNumber);
}
