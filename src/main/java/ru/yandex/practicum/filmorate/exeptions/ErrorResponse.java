package ru.yandex.practicum.filmorate.exeptions;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String error;
    private final String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}