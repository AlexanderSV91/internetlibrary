package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.User;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSenderService {

    void sendTextEmail(String userMail, String token);

    void sendImageMail(String userMail, String token) throws Exception;

    void sendActiveEmail(User user, String token) throws MessagingException, IOException, TemplateException;
}
