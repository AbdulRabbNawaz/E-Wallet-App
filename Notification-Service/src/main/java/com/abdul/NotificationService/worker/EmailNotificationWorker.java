package com.abdul.NotificationService.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationWorker {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmailNotificationToUser(String name, String email, String identifier){

        String mailBody = "Hi " + name+"\n"+"Welcome to the E-Wallet Application, your account " +
                "has been created successfully" +
                ". Your Identification is done by using "+identifier+"\n"+""+
                "Thanks for singing up";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Welcome to E-Wallet Application");;
        simpleMailMessage.setText(mailBody);
        simpleMailMessage.setTo(email);

        javaMailSender.send(simpleMailMessage);
    }
}
