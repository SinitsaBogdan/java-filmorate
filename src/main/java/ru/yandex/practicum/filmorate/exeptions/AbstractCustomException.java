package ru.yandex.practicum.filmorate.exeptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCustomException extends RuntimeException {

    @NonNull
    private final String name;

    @NonNull
    private final String description;

    @NonNull
    private final Integer httpStatusCode;

    protected AbstractCustomException(@NonNull String name, @NonNull String description, @NonNull Integer httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
