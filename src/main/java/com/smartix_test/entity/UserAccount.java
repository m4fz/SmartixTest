package com.smartix_test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "users") //postgres already has a table called user
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    private BigInteger userBalance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<UserPayment> userPaymentList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;


}
