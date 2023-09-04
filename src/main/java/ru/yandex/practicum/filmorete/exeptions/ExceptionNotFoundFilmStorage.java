package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.message.FilmErrorMessage;
import ru.yandex.practicum.filmorete.exeptions.message.ValidFilmErrorMessage;

@Slf4j
public class ExceptionNotFoundFilmStorage extends AbstractCustomException {

    public ExceptionNotFoundFilmStorage(@NotNull ValidFilmErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }

    public ExceptionNotFoundFilmStorage(@NotNull FilmErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}