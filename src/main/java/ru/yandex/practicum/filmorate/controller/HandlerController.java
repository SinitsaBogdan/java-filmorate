package ru.yandex.practicum.filmorate.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exeptions.*;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(ExceptionValidationFilm.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull ExceptionValidationFilm exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(ExceptionValidationUser.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull ExceptionValidationUser exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(ExceptionNotFoundUserStorage.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull ExceptionNotFoundUserStorage exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(ExceptionNotFoundFilmStorage.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull ExceptionNotFoundFilmStorage exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(ExceptionNotFoundGenreStorage.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull ExceptionNotFoundGenreStorage exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(ExceptionNotFoundMpaStorage.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull ExceptionNotFoundMpaStorage exception) {
        ErrorResponse response = new ErrorResponse(exception.getName(), exception.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getHttpStatusCode()));
    }
}