package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String SUBJECT = "Mail Confirmation Link!";
    private static final String CONFIRMATION_URL = "confirm/";
    private static final String NAME_TEMPLATE = "activeEmail.ftl";
    @Value(value = "${server.host}")
    private String SERVER_HOST;

    private final JavaMailSenderImpl mailSender;
    private final FreeMarkerConfigurer markerConfigurer;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSenderImpl mailSender,
                                  FreeMarkerConfigurer markerConfigurer) {
        this.mailSender = mailSender;
        this.markerConfigurer = markerConfigurer;
    }

    @Async
    public void sendActiveEmail(User user, String token) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom("<MAIL>");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(SUBJECT);

        String s = getTemplate(user, token);
        mimeMessageHelper.setText(s, true);
        mailSender.send(mimeMessage);
    }

    private String getTemplate(User user, String token) throws IOException, TemplateException {
        String activeLink = SERVER_HOST + CONFIRMATION_URL + token;

        Map<String, String> map = new HashMap<>();
        map.put("userToken", token);
        map.put("activeLink", activeLink);
        map.put("userName", user.getUserName());

        Configuration configuration = markerConfigurer.getConfiguration();
        Template template = configuration.getTemplate(NAME_TEMPLATE, "UTF-8");

        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        stringWriter.flush();
        return stringWriter.toString();
    }
}
