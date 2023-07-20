package ru.yandex.practicum.filmorete.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValidationFilmException.class)
    public ResponseEntity<ErrorResponse> handleException(ValidationFilmException exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(ValidationUserException.class)
    public ResponseEntity<ErrorResponse> handleException(ValidationUserException exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }
}