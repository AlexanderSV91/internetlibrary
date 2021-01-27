package com.faceit.example.internetlibrary.util;

import com.faceit.example.internetlibrary.exception.ApiException;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.model.Role;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public static boolean isEmployee(Set<Role> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ROLE_EMPLOYEE));
    }

    public static ApiException buildApiException(String simpleName,
                                                 Map<String, String> message,
                                                 HttpStatus badRequest,
                                                 LocalDateTime now) {
        return new ApiException(simpleName, message, badRequest, now);
    }

    public static Map<String, String> buildMap(String key, String value) {
        return new HashMap<>() {{
            put(key, value);
        }};
    }

    public static <T> T getDataFromTypeOptional(Optional<T> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException("exception.notFound"));
    }

    public static String getMessageForLocale(String messageKey) {
        Locale locale = new Locale(LocaleContextHolder.getLocale().toString());
        return ResourceBundle.getBundle("messages", locale).getString(messageKey);
    }
}
