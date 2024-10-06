package com.abdul.WalletService.service;

import CommonConstants.CommonConstants;
import CommonConstants.model.UserIdentifier;
import com.abdul.WalletService.model.Wallet;
import com.abdul.WalletService.repository.WalletRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    @Value("${balance.initial.amount}")
    String balance;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void createWalletAccount(String data){

        JSONObject userDetailsObject = new JSONObject(data);

        String name = userDetailsObject.optString(CommonConstants.USER_NAME);
        String email = userDetailsObject.optString(CommonConstants.USER_EMAIL);
        String userIdentifier = userDetailsObject.optString(CommonConstants.USER_IDENTIFIER);
        String mobileNo = userDetailsObject.optString(CommonConstants.USER_MOBILE_NO);
        String userIdentifierValue = userDetailsObject.optString(CommonConstants.USER_IDENTIFIER_VALUE);

        Wallet wallet = Wallet.builder()
                .userId(email)
                .mobileNo(mobileNo)
                .userIdentifier(UserIdentifier.valueOf(userIdentifier))
                .userIdentifierValue(userIdentifierValue)
                .balance(Double.parseDouble(balance))
                .build();

        walletRepository.save(wallet);

        System.out.println("Wallet Account has been created successfully");
    }


    public void processTransaction(String senderId, String receiverId, double amount, String txnId){

        Wallet wallet = walletRepository.findByMobileNo(receiverId);
        Wallet senderWallet = walletRepository.findByMobileNo(senderId);

        String txnMessage = "";
        String txnStatus = "";

        if(wallet == null){
            txnMessage = "Sender Account Does not exists";
            txnStatus = "FAILED";
        }
        else if(senderWallet.getBalance() < amount){
            txnMessage = "Insufficient Balance";
            txnStatus = "FAILED";
        }
        else{
            if(processAmount(senderId, receiverId, amount)){
                txnMessage = "Transaction Success";
                txnStatus = "SUCCESS";
            }
            else{
                txnMessage = "There is some technical issue";
                txnStatus = "FAILED";
            }
        }

        // Pushing data in kafka

        JSONObject txnObject = new JSONObject();
        txnObject.put(CommonConstants.TXN_ID, txnId);
        txnObject.put(CommonConstants.TXN_STATUS, txnStatus);
        txnObject.put(CommonConstants.TXN_MESSAGE, txnMessage);


        kafkaTemplate.send("txn-update", txnObject.toString());
    }

    @Transactional
    public boolean processAmount(String sender, String receiver, double amount){

        boolean isDone = false;
        try {
            walletRepository.updateWalletBalance(sender, -amount);
            walletRepository.updateWalletBalance(receiver, amount);
            isDone = true;
        }
        catch (Exception e){
            isDone = false;
        }

        return isDone;
    }

}
