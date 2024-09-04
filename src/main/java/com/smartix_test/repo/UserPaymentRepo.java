package com.smartix_test.repo;

import com.smartix_test.entity.UserPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepo extends JpaRepository<UserPayment, Long> {
    @Query(value = "SELECT payment FROM UserPayment as payment WHERE payment.account.userId = :userId") //hql
    Page<UserPayment> findByAccount(@Param("userId") Long userId, Pageable pageable);
}
