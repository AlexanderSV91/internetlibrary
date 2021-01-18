package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final JavaMailSenderImpl mailSender;
    private final FreeMarkerConfigurer markerConfigurer;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender,
                                  JavaMailSenderImpl mailSender,
                                  FreeMarkerConfigurer markerConfigurer) {
        this.javaMailSender = javaMailSender;
        this.mailSender = mailSender;
        this.markerConfigurer = markerConfigurer;
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
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
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

    @Async
    public void sendActiveEmail(User user, String token) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom("<MAIL>");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Mail Confirmation Link!");

        String s = getTemplate(user, token);
        mimeMessageHelper.setText(s, true);
        mailSender.send(mimeMessage);
    }

    private String getTemplate(User user, String token) throws IOException, TemplateException {
        String activeLink = "http://localhost:8080/api-public/confirm/" + token;

        Map<String, String> map = new HashMap<>();
        map.put("userToken", token);
        map.put("activeLink", activeLink);
        map.put("userName", user.getUserName());

        Configuration configuration = markerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("activeEmail.ftl", "UTF-8");

        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        stringWriter.flush();
        return stringWriter.toString();
    }
}
