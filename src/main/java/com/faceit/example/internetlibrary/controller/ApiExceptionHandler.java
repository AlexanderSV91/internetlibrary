package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.exception.ApiException;
import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.exception.ResourceAlreadyExists;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handlerApiRequestException(ApiRequestException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> message = buildMap(e.getMessage(), e.getMessage());
        ApiException apiException = buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ResourceAlreadyExists.class})
    public ResponseEntity<Object> handlerResourceAlreadyExistException(ResourceAlreadyExists e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        Map<String, String> message = buildMap(e.getMessage(), e.getNameResource());
        ApiException apiException = buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Map<String, String> message = buildMap(e.getMessage(), ResourceNotFoundException.NOT_FOUND);

        ApiException apiException = buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleValidationExceptions(ConstraintViolationException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> message = new HashMap<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String fieldName = constraintViolation.getPropertyPath().toString();
            String errorMessage = constraintViolation.getMessage();
            message.put(fieldName, errorMessage);
        });

       ApiException apiException = buildApiException(
               e.getClass().getSimpleName(),
               message,
               httpStatus,
               LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> message = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            message.put(fieldName, errorMessage);
        });

        ApiException apiException = buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    private ApiException buildApiException(String simpleName,
                                           Map<String, String> message,
                                           HttpStatus badRequest,
                                           LocalDateTime now) {
        return new ApiException(simpleName, message, badRequest, now);
    }

    private Map<String, String> buildMap(String key, String value) {
        return new HashMap<>(){{put(key,value);}};
    }
}
