package com.abdul.WalletService.repository;

import com.abdul.WalletService.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByMobileNo(String mobileNo);

    @Transactional
    @Modifying
    @Query("update wallet w set w.balance = w.balance +: amount where w.mobileNo =: mobile")
    void updateWalletBalance(String mobile, double amount);
}
