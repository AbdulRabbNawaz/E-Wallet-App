package com.abdul.WalletService.listener;

import CommonConstants.CommonConstants;
import com.abdul.WalletService.service.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionDetailsListener {

    @Autowired
    WalletService walletService;

    @KafkaListener(topics = "txn-details", groupId = "txn-group")
    public void ListenTransactionData(String data){

        System.out.println("Transaction Data Received for processing");

        JSONObject jsonObject = new JSONObject(data);
        String senderId = jsonObject.optString(CommonConstants.SENDER_ID);
        String receiverId = jsonObject.optString(CommonConstants.RECEIVER_ID);
        double amount = jsonObject.optDouble(CommonConstants.AMOUNT);
        String txnId = jsonObject.optString(CommonConstants.TXN_ID);

        walletService.processTransaction(senderId, receiverId, amount, txnId);

    }
}
