package com.abdul.TransactionService.service;

import CommonConstants.CommonConstants;
import com.abdul.TransactionService.model.Transaction;
import com.abdul.TransactionService.model.enums.TxnStatus;
import com.abdul.TransactionService.repository.TransactionRepository;
import com.abdul.TransactionService.request.TransactionRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public String initiateTransaction(String senderId, TransactionRequest transactionRequest){
        String receiverId = transactionRequest.getReceiverId();
        double amount = transactionRequest.getAmount();
        String purpose = transactionRequest.getPurpose();

        String txnId = UUID.randomUUID().toString();

        Transaction transaction = Transaction.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .amount(amount)
                .purpose(purpose)
                .txnId(txnId)
                .txnStatus(TxnStatus.INITIATED)
                .build();

        transactionRepository.save(transaction);

        JSONObject txnObject = new JSONObject();
        txnObject.put(CommonConstants.SENDER_ID, senderId);
        txnObject.put(CommonConstants.RECEIVER_ID, receiverId);
        txnObject.put(CommonConstants.AMOUNT, amount);
        txnObject.put(CommonConstants.TXN_ID, txnId);

        kafkaTemplate.send("txn-details", txnObject.toString());

        return txnId;
    }

    @KafkaListener(topics = "txn-update", groupId = "txn-update-group")
    public void updateTransactionState(String data){
        System.out.println("Transaction update Data Received: " + data);

        JSONObject jsonObject = new JSONObject(data);
        String txnId = jsonObject.optString(CommonConstants.TXN_ID);
        String message = jsonObject.getString(CommonConstants.TXN_MESSAGE);
        String txnStatus = jsonObject.optString(CommonConstants.TXN_STATUS);

        transactionRepository.updateTransaction(txnId, TxnStatus.valueOf(txnStatus), message);

        System.out.println("Transaction has been updated");
    }

}
