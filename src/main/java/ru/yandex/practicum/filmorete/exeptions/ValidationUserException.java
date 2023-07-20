package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUserException extends RuntimeException {

    private final String name;
    private final String description;
    private final Integer httpStatusCode;

    public ValidationUserException(MessageErrorValidUser error) {
        super(error.toString());
        this.name = error.name;
        this.description = error.description;
        this.httpStatusCode = error.httpStatusCode;
        log.debug(this.getMessage());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
