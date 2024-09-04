package com.smartix_test.exception;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException(String message){
        super(message);
    }
}
