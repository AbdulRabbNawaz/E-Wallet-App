package com.abdul.WalletService.model;

import CommonConstants.model.UserIdentifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userId;

    @Column(nullable = false, unique = true)
    private String mobileNo;

    @Enumerated(EnumType.STRING)
    private UserIdentifier userIdentifier;

    @Column(nullable = false)
    private String userIdentifierValue;

    private double balance;

    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
