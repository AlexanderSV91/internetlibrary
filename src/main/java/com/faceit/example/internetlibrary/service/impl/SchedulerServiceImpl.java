package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.ConfirmationToken;
import com.faceit.example.internetlibrary.model.enumeration.TokenStatus;
import com.faceit.example.internetlibrary.service.ConfirmationTokenService;
import com.faceit.example.internetlibrary.service.EmailSenderService;
import com.faceit.example.internetlibrary.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private static final String CRON = "0 0 */1 * * *";

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;

    @Autowired
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
                LocalDateTime issuedDate = confirmationToken.getIssuedDate();
                LocalDateTime currentDate = LocalDateTime.now();
                Duration duration = Duration.between(currentDate, issuedDate.plusDays(2));

                if (duration.toMinutes() <= 60 && duration.toMinutes() >= 0) {
                    System.out.println(duration.toMinutes());
                    System.out.println(TokenStatus.PENDING);
                    try {
                        emailSenderService.sendActiveEmail(confirmationToken.getUser(),confirmationToken.getToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (duration.toMinutes() < 0) {
                    System.out.println(duration.toMinutes());
                    System.out.println(TokenStatus.EXPIRED);
                    confirmationToken.setStatus(TokenStatus.EXPIRED);
                    confirmationTokenService.updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
                }
            }
        }
    }
}
