package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.message.ReviewErrorMessage;

@Slf4j
public class ExceptionNotFoundReviewStorage extends AbstractCustomException {

    public ExceptionNotFoundReviewStorage(@NotNull ReviewErrorMessage error) {
        super(error.name, error.description, error.httpStatusCode);
        log.debug(this.getMessage());
    }
}