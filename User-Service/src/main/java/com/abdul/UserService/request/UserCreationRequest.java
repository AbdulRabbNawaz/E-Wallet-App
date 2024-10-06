package com.abdul.UserService.request;

import com.abdul.UserService.model.User;
import com.abdul.UserService.model.enums.UserIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {

    private String name;
    private String email;
    private String dob;
    private String mobileNo;
    private String password;
    private UserIdentifier userIdentifier;
        private String userIdentifierValue;

    public User BuildUser(){
        User user = User.builder()
                .name(this.name)
                .email(this.email)
                .dob(this.dob)
                .mobileNo(this.mobileNo)
                .password(this.password)
                .userIdentifier(this.userIdentifier)
                .userIdentifierValue(this.userIdentifierValue)
                .build();

        return user;
    }
}
