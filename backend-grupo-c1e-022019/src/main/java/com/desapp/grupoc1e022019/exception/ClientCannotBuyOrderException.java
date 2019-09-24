package com.desapp.grupoc1e022019.exception;

public class ClientCannotBuyOrderException extends RuntimeException {

    public ClientCannotBuyOrderException(String message){
        super(message);
    }
}
