package ru.yandex.practicum.filmorete.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExceptionNotFoundEventStorage extends RuntimeException {
}