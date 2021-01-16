package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.model.ConfirmationToken;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.model.enumeration.TokenStatus;
import com.faceit.example.internetlibrary.repository.ConfirmationTokenRepository;
import com.faceit.example.internetlibrary.service.ConfirmationTokenService;
import com.faceit.example.internetlibrary.service.EmailSenderService;
import com.faceit.example.internetlibrary.service.UserService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
    private final UserService userService;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository,
                                        EmailSenderService emailSenderService,
                                        UserService userService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
    }

    @Override
    public void addConfirmationToken(User newUser) {
        User user = userService.addUser(newUser);
        ConfirmationToken confirmationToken =
                confirmationTokenRepository.save(preparingToConfirmationToken(user));
        sendConfirmationMail(user.getEmail(), confirmationToken.getToken());
    }

    @Override
    public boolean findByToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken.getStatus().equals(TokenStatus.PENDING)) {
            if (confirmationToken.getIssuedDate().isAfter(LocalDateTime.now().minusDays(2))) {
                User user = confirmationToken.getUser();
                user.setEnabled(true);
                userService.updateUserById(user, user.getId());

                confirmationToken.setStatus(TokenStatus.VERIFIED);
                updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
                return true;
            } else {
                confirmationToken.setStatus(TokenStatus.EXPIRED);
                updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
                throw new ApiRequestException("Token expired");
            }
        } else {
            throw new ApiRequestException("Token verified");
        }
    }

    @Override
    public ConfirmationToken getConfirmationTokenById(long id) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalConfirmationToken);
    }

    @Override
    public boolean existsByToken(String token) {
        return confirmationTokenRepository.existsByToken(token);
    }

    @Override
    public ConfirmationToken updateConfirmationTokenById(ConfirmationToken updateConfirmationToken, long id) {
        ConfirmationToken confirmationToken = getConfirmationTokenById(id);
        if (confirmationToken != null) {
            updateConfirmationToken.setId(id);
        } else {
            throw new ResourceNotFoundException("Not found");
        }
        confirmationTokenRepository.save(updateConfirmationToken);
        return updateConfirmationToken;
    }

    private ConfirmationToken preparingToConfirmationToken(User newUser) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(newUser);
        confirmationToken.setIssuedDate(LocalDateTime.now());
        confirmationToken.setStatus(TokenStatus.PENDING);

        String token = UUID.randomUUID().toString();
        while (existsByToken(token)) {
            token = UUID.randomUUID().toString();
        }
        confirmationToken.setToken(token);
        return confirmationToken;
    }

    void sendConfirmationMail(String userMail, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account."
                        + "http://localhost:8080/api-public/confirm/" + token);
        emailSenderService.sendEmail(mailMessage);
    }
}
