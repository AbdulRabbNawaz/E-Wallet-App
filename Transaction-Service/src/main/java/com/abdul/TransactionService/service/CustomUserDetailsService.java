package com.abdul.TransactionService.service;


import org.json.JSONObject;
import CommonConstants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String url = "http://localhost:8081/user-service/get/user?username=" + username;

        String userAuthResponse = restTemplate.getForObject(url, String.class);

        JSONObject authJsonObject = new JSONObject(userAuthResponse);

        String user = authJsonObject.optString(CommonConstants.USER_MOBILE_NO);
        String password = authJsonObject.optString(CommonConstants.PASSWORD);
        String role = authJsonObject.optString(CommonConstants.USER_ROLE);


        UserDetails userDetails = User.builder()
                .username(user)
                .password(password)
                .roles(role)
                .build();

        return userDetails;
    }
}
