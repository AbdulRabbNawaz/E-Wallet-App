package com.abdul.TransactionService.controller;

import com.abdul.TransactionService.request.TransactionRequest;
import com.abdul.TransactionService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    @RequestMapping("/initiate/transaction")
    public String initiateTransaction(@RequestBody TransactionRequest transactionRequest){

        System.out.println("Inside the initiate transaction method");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String senderMobile = userDetails.getUsername();

//        String txnId = transactionService.initiateTransaction(senderMobile, transactionRequest);

        return null;
    }

}
