package com.firisbe.SecurePay.exception;

public class GenericCrudException extends RuntimeException{

    public GenericCrudException(String message) {
        super(message);
    }

    public GenericCrudException(String message, Throwable cause) {
        super(message, cause);
    }
}
