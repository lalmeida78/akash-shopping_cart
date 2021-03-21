package com.celfocus.onlineshop.Exception;

public class MutationNotAllowedException extends RuntimeException{

    public MutationNotAllowedException(String message) {
        super(message);
    }

    public MutationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
