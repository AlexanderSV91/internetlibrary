package com.faceit.example.internetlibrary.exception;

public class ResourceNotFoundException extends RuntimeException {

    public static final String NOT_FOUND = "Not found";

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
