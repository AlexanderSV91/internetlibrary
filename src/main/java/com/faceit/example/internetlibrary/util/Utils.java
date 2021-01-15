package com.faceit.example.internetlibrary.util;

import com.faceit.example.internetlibrary.exception.ApiException;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.model.Role;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        return new HashMap<>(){{put(key,value);}};
    }

    public static <T> T getDataFromTypeOptional(Optional<T> optional) {
        T data;
        if (optional.isPresent()) {
            data = optional.get();
        } else {
            throw new ResourceNotFoundException("Not found");
        }
        return data;
    }
}
