package com.celfocus.onlineshop.Exception;

public class ChecksumGenerationException extends RuntimeException{

    public ChecksumGenerationException(String message) {
        super(message);
    }

    public ChecksumGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
