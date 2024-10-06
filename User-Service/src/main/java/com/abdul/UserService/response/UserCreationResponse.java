package com.abdul.UserService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationResponse {

    String name;
    String email;
    String mobile;
    String message;
}
