package com.firisbe.SecurePay.exception;

public class CustomerCreditCardDidNotMatchException extends RuntimeException{

    public CustomerCreditCardDidNotMatchException(String message) {
        super(message);
    }
}
