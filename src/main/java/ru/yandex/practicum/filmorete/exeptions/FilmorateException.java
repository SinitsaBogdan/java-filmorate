package ru.yandex.practicum.filmorete.exeptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class FilmorateException extends RuntimeException {

    @NonNull
    private final String name;

    @NonNull
    private final String description;

    @NonNull
    private final Integer httpStatusCode;

    public FilmorateException(ResponseErrorMessage error) {
        this.name = error.getName();
        this.description = error.getDescription();
        this.httpStatusCode = error.getHttpStatusCode();
        log.debug("Пользовательское исключение : {} | {} | {}", error.getHttpStatusCode(), error.getName(), error.getDescription());
    }
}