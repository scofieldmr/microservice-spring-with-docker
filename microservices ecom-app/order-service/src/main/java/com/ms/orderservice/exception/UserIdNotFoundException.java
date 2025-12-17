package com.ms.orderservice.exception;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
