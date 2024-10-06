package com.abdul.UserService.controller;

import com.abdul.UserService.request.UserCreationRequest;
import com.abdul.UserService.response.UserCreationResponse;
import com.abdul.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @RequestMapping("/create/user")
    public ResponseEntity<UserCreationResponse> onboardUser(@RequestBody UserCreationRequest userCreationRequest){

        UserCreationResponse userCreationResponse = userService.createUserInDatabase(userCreationRequest);

        if(!userCreationResponse.getMessage().contains("created")){
            return new ResponseEntity<>(userCreationResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(userCreationResponse, HttpStatus.CREATED);
        }
    }

    @GetMapping
    @RequestMapping("/get/user")
    public String getUserInformation(@RequestParam("username") String username){

        return userService.getUserInformationByMobile(username);
    }
}
