package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.ConfirmationToken;
import com.faceit.example.internetlibrary.model.User;

public interface ConfirmationTokenService {

    void addConfirmationToken(User newUser);

    boolean findByToken(String token);

    ConfirmationToken getConfirmationTokenById(long id);

    boolean existsByToken(String token);

    ConfirmationToken updateConfirmationTokenById(ConfirmationToken updateConfirmationToken, long id);
}
