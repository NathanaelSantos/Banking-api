package org.acme.Exception.bank;

public class NoBanksFoundException extends RuntimeException{
    public NoBanksFoundException() {
        super("No banks found in the database");
    }
}
