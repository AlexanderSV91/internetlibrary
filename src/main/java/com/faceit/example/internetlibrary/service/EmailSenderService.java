package com.faceit.example.internetlibrary.service;

public interface EmailSenderService {

    void sendTextEmail(String userMail, String token);

    void sendHtmlMail(String userMail, String token) throws Exception;

    void sendImageMail(String userMail, String token) throws Exception;
}
