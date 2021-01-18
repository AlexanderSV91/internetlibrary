package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.Path;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final JavaMailSenderImpl mailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender, JavaMailSenderImpl mailSender) {
        this.javaMailSender = javaMailSender;
        this.mailSender = mailSender;
    }

    @Async
    public void sendTextEmail(String userMail, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account."
                + "http://localhost:8080/api-public/confirm/" + token);
        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendImageMail(String userMail, String token) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(userMail);
        mimeMessageHelper.setSubject("Mail Confirmation Link!");
        mimeMessageHelper.setFrom("<MAIL>");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>Thank you for registering.</h1><p>Please click on the below image to activate your account.</p>");
        sb.append("<a href=\"http://localhost:8080/api-public/confirm/" + token + "\"><img src=\"cid:imageId\"/></a></body>");
        sb.append("</html>");
        mimeMessageHelper.setText(sb.toString(), true);

        FileSystemResource img = new FileSystemResource("src/main/resources/images/confirm.jpg");
        mimeMessageHelper.addInline("imageId", img);

        mailSender.send(mimeMessage);
    }
}
