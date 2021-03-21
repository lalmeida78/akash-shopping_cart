package com.celfocus.onlineshop.Exception;

import com.celfocus.onlineshop.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    // Custom error code.
    private  Integer NOT_FOUND_CODE = 4004;

    private  Integer FORBIDDEN = 4003;

    private Integer CONFLICT = 4009;

    @ExceptionHandler({CartNotFoundException.class})
    public ResponseEntity<Error> handleException(CartNotFoundException e) {
        Error error = new Error();
        error.setCode(NOT_FOUND_CODE);
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler({MutationNotAllowedException.class})
    public ResponseEntity<Error> handleMutationException(MutationNotAllowedException e) {
        Error error = new Error();
        error.setCode(FORBIDDEN);
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler({ChecksumGenerationException.class})
    public ResponseEntity<Error> handleCheckSumException(ChecksumGenerationException e) {
        Error error = new Error();
        error.setCode(CONFLICT);
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler({CartProcessingException.class})
    public ResponseEntity<Error> handleCartProcessingException(CartProcessingException e) {
        Error error = new Error();
        error.setCode(CONFLICT);
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

}