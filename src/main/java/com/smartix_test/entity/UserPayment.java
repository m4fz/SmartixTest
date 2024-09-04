package com.smartix_test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_payment_list")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserPayment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(nullable = false)
    private LocalDateTime paymentDate;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private BigInteger paymentAmount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount account;

}
