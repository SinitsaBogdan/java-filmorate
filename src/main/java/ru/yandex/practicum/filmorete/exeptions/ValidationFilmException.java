package ru.yandex.practicum.filmorete.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationFilmException extends RuntimeException {

    public ValidationFilmException(MessageErrorValidFilm message, int statusCode) {
        super(message.toString());
        log.debug(message.toString());
        throw new ResponseStatusException(HttpStatus.valueOf(statusCode), message.toString());
    }
}

