package ru.yandex.practicum.filmorete.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorete.exeptions.*;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler
    public ResponseEntity<ResponseError> handleException(@NotNull FilmorateException exception) {
        return new ResponseEntity<>(
                ResponseError.builder()
                        .name(exception.getName())
                        .description(exception.getDescription())
                        .build(),
                HttpStatus.valueOf(exception.getHttpStatusCode())
        );
    }
}