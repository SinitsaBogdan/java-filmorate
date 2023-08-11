package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ExceptionValidationUser extends AbstractCustomException {

    public ExceptionValidationUser(@NotNull MessageErrorValidUser error) {
        super(
                error.name,
                error.description,
                error.httpStatusCode
        );
        log.debug(this.getMessage());
    }
}