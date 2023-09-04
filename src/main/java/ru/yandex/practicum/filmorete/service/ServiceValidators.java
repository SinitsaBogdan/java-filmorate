package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidation;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorete.exeptions.message.ValidFilmErrorMessage.*;
import static ru.yandex.practicum.filmorete.exeptions.message.UserErrorMessage.ERROR_USER_LOGIN_IS_WHITESPACE;
import static ru.yandex.practicum.filmorete.exeptions.message.UserErrorMessage.ERROR_USER_NOT_LOGIN;

@Slf4j
public class ServiceValidators {

    public static void checkValidUser(@NotNull User user) {
        if (user.getName() == null || user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getLogin().isBlank()) throw new ExceptionValidation(ERROR_USER_NOT_LOGIN);
        if (user.getLogin().contains(" ")) throw new ExceptionValidation(ERROR_USER_LOGIN_IS_WHITESPACE);
        log.error("Успешная валидация USER {}", user);
    }

    public static void checkValidFilm(@NotNull Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ExceptionValidation(ERROR_FILM_RELEASED_MIN);
        }
        if (film.getDuration() == null) throw new ExceptionValidation(ERROR_FILM_NOT_DURATION);
        if (film.getDuration() < 0) throw new ExceptionValidation(ERROR_FILM_DURATION_MIN);
        log.error("Успешная валидация FILM {}", film);
    }
}
