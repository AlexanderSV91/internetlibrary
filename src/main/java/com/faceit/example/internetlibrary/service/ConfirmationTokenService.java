package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.enumeration.TokenStatus;
import com.faceit.example.internetlibrary.model.mysql.ConfirmationToken;
import com.faceit.example.internetlibrary.model.mysql.User;

import java.util.List;

public interface ConfirmationTokenService {

    void addConfirmationToken(User newUser);

    TokenStatus findByToken(String token);

    ConfirmationToken getConfirmationTokenById(long id);

    boolean existsByToken(String token);

    ConfirmationToken updateConfirmationTokenById(ConfirmationToken updateConfirmationToken, long id);

    List<ConfirmationToken> getAllConfirmationToken();
}
