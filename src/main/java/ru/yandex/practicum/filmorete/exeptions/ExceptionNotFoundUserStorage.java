package ru.yandex.practicum.filmorete.exeptions;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ExceptionNotFoundUserStorage extends RuntimeException {
    @NonNull
    private final String name;

    @NonNull
    private final String description;

    @NonNull
    private final Integer httpStatusCode;

    public ExceptionNotFoundUserStorage(MessageErrorValidUser error) {
        super(error.description);
        this.name = error.name;
        this.description = error.description;
        this.httpStatusCode = error.httpStatusCode;
        log.debug(this.getMessage());
    }
}
