package ru.yandex.practicum.filmorate.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ExceptionValidationFilm extends AbstractCustomException {

    public ExceptionValidationFilm(@NotNull MessageErrorValidFilm error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}