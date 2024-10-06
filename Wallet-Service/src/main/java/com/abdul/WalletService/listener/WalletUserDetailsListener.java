package com.abdul.WalletService.listener;

import CommonConstants.CommonConstants;
import com.abdul.WalletService.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WalletUserDetailsListener {

    @Autowired
    WalletService walletService;

    @KafkaListener(topics = CommonConstants.USER_DETAILS_TOPIC, groupId = CommonConstants.WALLET_CONSUMER_GROUP)
    public void listenUserDataForWalletAccount(String data){

        System.out.println("Data Received for Wallet Account: " + data);

        walletService.createWalletAccount(data);

    }
}
