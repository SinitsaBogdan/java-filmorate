package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ValidationFilmException extends RuntimeException {

    private String name;
    private String description;
    private Integer httpStatusCode;

    public ValidationFilmException(@NotNull MessageErrorValidFilm error) {
        super(error.getDescription());
        this.name = error.getName();
        this.description = error.getDescription();
        this.httpStatusCode = error.getHttpStatusCode();
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

