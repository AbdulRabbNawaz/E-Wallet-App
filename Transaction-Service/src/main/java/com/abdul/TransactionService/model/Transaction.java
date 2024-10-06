package com.abdul.TransactionService.model;

import com.abdul.TransactionService.model.enums.TxnStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "txn_table")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String senderId;
    private String receiverId;
    private String purpose;

    @Column(unique = true)
    private String txnId;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TxnStatus txnStatus;
    private String txnMessage;
}

