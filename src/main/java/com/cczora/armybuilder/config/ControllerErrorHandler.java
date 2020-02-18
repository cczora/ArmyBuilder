package com.cczora.armybuilder.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ControllerErrorHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_MESSAGE = "This error is either a 500 or isn't handled by the ControllerErrorHandler yet: ";
    private static final String USER_NOT_VALIDATED = "User %s is not authorized to make this request.";
    private static final String USER_NOT_FOUND = "User not found, please correct your input and try again.";

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Error> handleValidationException(ValidationException ex, WebRequest request) {
        return new ResponseEntity<>(
                Error.builder()
                        .message(String.format(USER_NOT_VALIDATED, request.getRemoteUser()))
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public final ResponseEntity<Error> handleNoSuchFieldException(NoSuchFieldException ex, WebRequest request) {
        return new ResponseEntity<>(
                Error.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Error> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
                Error.builder()
                        .message(ex.getMessage() + "not found.")
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersistenceException.class)
    public final ResponseEntity<Error> handlePersistenceException(PersistenceException ex, WebRequest request) {
        return new ResponseEntity<>(
                Error.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> catchAllErrorHandler(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                Error.builder()
                        .message(DEFAULT_MESSAGE + ex.getCause().getCause().getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
