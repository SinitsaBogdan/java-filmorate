package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.message.TotalFilmLikeErrorMessage;

@Slf4j
public class ExceptionTotalFilmLikeStorage extends AbstractCustomException {

    public ExceptionTotalFilmLikeStorage(@NotNull TotalFilmLikeErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}