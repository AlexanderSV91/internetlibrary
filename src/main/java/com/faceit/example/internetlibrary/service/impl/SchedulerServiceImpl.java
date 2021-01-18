package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.ConfirmationToken;
import com.faceit.example.internetlibrary.model.enumeration.TokenStatus;
import com.faceit.example.internetlibrary.service.ConfirmationTokenService;
import com.faceit.example.internetlibrary.service.EmailSenderService;
import com.faceit.example.internetlibrary.service.SchedulerService;
import freemarker.template.TemplateException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private static final String CRON = "*/30 * * * * *";
    //static final LocalDateTime issuedDate = LocalDateTime.now().minusMinutes(59);

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;

    public SchedulerServiceImpl(ConfirmationTokenService confirmationTokenService,
                                EmailSenderService emailSenderService) {
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }


    @Override
    @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        List<ConfirmationToken> confirmationTokens = confirmationTokenService.getAllConfirmationToken();
        for (ConfirmationToken confirmationToken : confirmationTokens) {
            TokenStatus tokenStatus = confirmationToken.getStatus();

            if (tokenStatus == TokenStatus.PENDING) {
                LocalDateTime issuedDate = confirmationToken.getIssuedDate().plusDays(2);
                LocalDateTime currentDate = LocalDateTime.now();
                Duration duration = Duration.between(issuedDate, currentDate);
                System.out.println(duration.toMinutes());
                if (duration.toMinutes() <= 60 && duration.toMinutes() >= 0) {
                    try {
                        emailSenderService.sendActiveEmail(confirmationToken.getUser(),confirmationToken.getToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (duration.toMinutes() < 0) {
                    confirmationToken.setStatus(TokenStatus.EXPIRED);
                    confirmationTokenService.updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
                }
            }
        }
    }
}
