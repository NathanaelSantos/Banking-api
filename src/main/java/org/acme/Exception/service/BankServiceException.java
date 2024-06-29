package org.acme.Exception.service;

public class BankServiceException extends RuntimeException{
    public BankServiceException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
