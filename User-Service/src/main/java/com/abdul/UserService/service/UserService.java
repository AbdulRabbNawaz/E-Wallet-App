package com.abdul.UserService.service;

import CommonConstants.CommonConstants;
import com.abdul.UserService.Repository.UserRepository;
import com.abdul.UserService.model.User;
import com.abdul.UserService.model.enums.UserStatus;
import com.abdul.UserService.model.enums.UserType;
import com.abdul.UserService.request.UserCreationRequest;
import com.abdul.UserService.response.UserCreationResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public UserCreationResponse createUserInDatabase(UserCreationRequest userCreationRequest) {

        User user = userCreationRequest.BuildUser();
        user.setUserType(UserType.NORMAL);
        user.setUserStatus(UserStatus.ACTIVE);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoder.encode(encryptedPassword));

        UserCreationResponse userCreationResponse = new UserCreationResponse();

        try {
            User createdUser = userRepository.save(user);

            // Constructing the object to send the ok response after the user creation
            userCreationResponse.setName(user.getName());
            userCreationResponse.setEmail(user.getEmail());
            userCreationResponse.setMobile(user.getMobileNo());

            if (createdUser == null) { //user not created in database
                userCreationResponse.setMessage(CommonConstants.USER_CREATION_FAILED);
            } else { // user created in database
                userCreationResponse.setMessage(CommonConstants.USER_CREATION_SUCCESS);
            }

            // Now Pushing data in kafka
            JSONObject kafkaUserObject = new JSONObject();
            kafkaUserObject.put(CommonConstants.USER_NAME, user.getName());
            kafkaUserObject.put(CommonConstants.USER_EMAIL, user.getEmail());
            kafkaUserObject.put(CommonConstants.USER_IDENTIFIER, user.getUserIdentifier().name());
            kafkaUserObject.put(CommonConstants.USER_IDENTIFIER_VALUE, user.getUserIdentifierValue());
            kafkaUserObject.put(CommonConstants.USER_MOBILE_NO, user.getMobileNo());

            kafkaTemplate.send(CommonConstants.USER_DETAILS_TOPIC, kafkaUserObject.toString());
        }
        catch(Exception e){
            userCreationResponse.setMessage(e.getMessage());
        }

        return userCreationResponse;
    }

    public String getUserInformationByMobile(String username){

        User user = userRepository.findByMobileNo(username);

        JSONObject userDetails = new JSONObject();

        userDetails.put(CommonConstants.USER_MOBILE_NO, user.getMobileNo());
        userDetails.put(CommonConstants.PASSWORD, user.getPassword());
        userDetails.put(CommonConstants.USER_ROLE, user.getUserType().name());

        System.out.println("Sending the response to transaction service: " + userDetails.toString());

        return userDetails.toString();
    }

}
