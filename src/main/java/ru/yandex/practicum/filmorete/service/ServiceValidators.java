package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorete.exeptions.FilmorateException;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorete.exeptions.ResponseErrorMessage.*;

@Slf4j
public class ServiceValidators {

    public static void checkValidUser(@NotNull User user) {
        if (user.getName() == null || user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getLogin().isBlank()) throw new FilmorateException(ERROR__USER__NOT_LOGIN);
        if (user.getLogin().contains(" ")) throw new FilmorateException(ERROR__USER__LOGIN_IS_WHITESPACE);
        log.error("Успешная валидация USER {}", user);
    }

    public static void checkValidFilm(@NotNull Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmorateException(ERROR__FILM__RELEASED_MIN);
        }
        if (film.getDuration() == null) throw new FilmorateException(ERROR__FILM__NOT_DURATION);
        if (film.getDuration() < 0) throw new FilmorateException(ERROR__FILM__DURATION_MIN);
        log.error("Успешная валидация FILM {}", film);
    }
}
