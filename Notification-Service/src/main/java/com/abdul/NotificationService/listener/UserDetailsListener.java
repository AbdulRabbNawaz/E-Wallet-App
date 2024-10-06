package com.abdul.NotificationService.listener;

import CommonConstants.CommonConstants;
import com.abdul.NotificationService.worker.EmailNotificationWorker;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsListener {

    @Autowired
    EmailNotificationWorker emailNotificationWorker;

    @KafkaListener(topics = CommonConstants.USER_DETAILS_TOPIC, groupId = CommonConstants.EMAIL_CONSUMER_GROUP)
    public void listenUserDetails(String data){

        System.out.println("Data Consumed: " + data);
        JSONObject userDetailsJson = new JSONObject(data);
        String name = userDetailsJson.optString(CommonConstants.USER_NAME);
        String email = userDetailsJson.optString(CommonConstants.USER_EMAIL);
        String identifier = userDetailsJson.optString(CommonConstants.USER_IDENTIFIER);

        emailNotificationWorker.sendEmailNotificationToUser(name, email, identifier);
    }
}
