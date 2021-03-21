package com.celfocus.onlineshop.Exception;

public class CartProcessingException extends RuntimeException{

    public CartProcessingException(String message) {
        super(message);
    }

    public CartProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
