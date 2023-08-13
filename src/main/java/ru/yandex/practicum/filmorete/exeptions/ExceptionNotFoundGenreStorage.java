package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ExceptionNotFoundGenreStorage extends AbstractCustomException {

    public ExceptionNotFoundGenreStorage(@NotNull MessageErrorServiceGenre error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}