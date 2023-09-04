package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.message.ValidFilmErrorMessage;
import ru.yandex.practicum.filmorete.exeptions.message.UserErrorMessage;

@Slf4j
public class ExceptionValidation extends AbstractCustomException {

    public ExceptionValidation(@NotNull ValidFilmErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }

    public ExceptionValidation(@NotNull UserErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}