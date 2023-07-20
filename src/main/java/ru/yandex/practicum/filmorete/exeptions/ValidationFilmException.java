package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ValidationFilmException extends RuntimeException {

    private final String name;
    private final String description;
    private final Integer httpStatusCode;

    public ValidationFilmException(@NotNull MessageErrorValidFilm error) {
        super(error.description);
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

