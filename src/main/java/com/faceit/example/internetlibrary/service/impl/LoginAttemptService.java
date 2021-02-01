package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;
import com.faceit.example.internetlibrary.model.mysql.User;
import com.faceit.example.internetlibrary.service.mysql.NumberAuthorizationService;
import com.faceit.example.internetlibrary.service.mysql.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginAttemptService {

    private final UserService userService;
    private final NumberAuthorizationService numberAuthorizationService;

    public LoginAttemptService(UserService userService,
                               NumberAuthorizationService numberAuthorizationService) {
        this.userService = userService;
        this.numberAuthorizationService = numberAuthorizationService;
    }

    public void loginSucceeded(String username) {
        NumberAuthorization numberAuthorization = userService
                .findUserByUserName(username).getNumberAuthorization();
        numberAuthorization.setLastAuthorizationDate(LocalDateTime.now());
        numberAuthorizationService.updateNumberAuthorizationById(numberAuthorization);
    }

    public void loginFailed(String username) {
        User user = userService.findUserByUserName(username);
        NumberAuthorization numberAuthorization = user.getNumberAuthorization();
        int numberOfAttempts = numberAuthorization.getQuantity();
        numberOfAttempts--;
        if (numberOfAttempts <= 0) {
            user.setEnabled(false);
            userService.updateUserById(user, user.getId());
        } else {
            numberAuthorization.setQuantity(numberOfAttempts);
            numberAuthorizationService.updateNumberAuthorizationById(numberAuthorization);
        }
    }
}
