package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.message.MpaErrorMessage;

@Slf4j
public class ExceptionNotFoundMpaStorage extends AbstractCustomException {

    public ExceptionNotFoundMpaStorage(@NotNull MpaErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}