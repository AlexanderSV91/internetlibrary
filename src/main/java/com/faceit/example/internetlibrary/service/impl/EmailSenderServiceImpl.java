package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.EmailSenderService;
import com.faceit.example.internetlibrary.util.Utils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.Locale;
import java.util.Map;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final String CONFIRMATION_URL = "confirm/";
    private static final String NAME_TEMPLATE = "activeEmail.ftl";

    @Value(value = "${server.host}")
    private String serverHost;

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
        Locale locale = new Locale(LocaleContextHolder.getLocale().toString());
        String subject = Utils.getMessageForLocale("activeEmail.mailConfirmationLink", locale);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom("<MAIL>");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(subject);

        String s = getTemplate(user, locale, token);
        mimeMessageHelper.setText(s, true);
        mailSender.send(mimeMessage);
    }

    private String getTemplate(User user, Locale locale, String token) throws IOException, TemplateException {
        String activeLink = serverHost + CONFIRMATION_URL + token;
        String newUser = Utils.getMessageForLocale("activeEmail.newUser", locale);
        String copyright = Utils.getMessageForLocale("activeEmail.copyright", locale);
        String yourToken = Utils.getMessageForLocale("activeEmail.yourToken", locale);
        String internetLibrary = Utils.getMessageForLocale("activeEmail.internetLibrary", locale);
        String confirmRegistration = Utils.getMessageForLocale("activeEmail.confirmRegistration", locale);

        Map<String, String> map = new HashMap<>();
        map.put("userToken", token);
        map.put("activeLink", activeLink);
        map.put("userName", user.getUserName());
        map.put("newUser", newUser);
        map.put("copyright", copyright);
        map.put("yourToken", yourToken);
        map.put("internetLibrary", internetLibrary);
        map.put("confirmRegistration", confirmRegistration);

        Configuration configuration = markerConfigurer.getConfiguration();
        Template template = configuration.getTemplate(NAME_TEMPLATE, "UTF-8");

        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        stringWriter.flush();
        return stringWriter.toString();
    }
}
