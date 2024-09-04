package com.smartix_test.exception;

public class UserAccountNotFoundException extends RuntimeException{
    public UserAccountNotFoundException(String message){
        super(message);
    }
}
