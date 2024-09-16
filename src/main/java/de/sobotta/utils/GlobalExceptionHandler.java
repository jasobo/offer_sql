package de.sobotta.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    public static class NotFoundException extends RuntimeException {

        public NotFoundException(String offerNr) {
            super("Offer not found with number: " + offerNr);
        }
    }

    public static class InvalidInputException extends IllegalArgumentException {
        public InvalidInputException(String input) {
            super("Invalid input: " + input);
        }
    }
}
