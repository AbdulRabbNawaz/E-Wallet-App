package com.abdul.TransactionService.repository;

import com.abdul.TransactionService.model.Transaction;
import com.abdul.TransactionService.model.enums.TxnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Transactional
    @Modifying
    @Query("update transaction t set t.txnStatus =: txnStatus, t.txnMessage =: txnMessage where t.txnId =: txnId")
    public void updateTransaction(String txnId, TxnStatus txnStatus, String txnMessage);
}
