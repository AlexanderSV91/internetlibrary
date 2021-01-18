package com.faceit.example.internetlibrary.service;

public interface EmailSenderService {

    void sendTextEmail(String userMail, String token);

    void sendImageMail(String userMail, String token) throws Exception;
}
