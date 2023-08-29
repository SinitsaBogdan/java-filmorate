package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.exeptions.MessageErrorValidFilm.*;
import static ru.yandex.practicum.filmorate.exeptions.MessageErrorValidUser.*;

@Slf4j
public class ServiceValidators {

    public static void checkValidUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().isBlank()) {
            throw new ExceptionValidationUser(VALID_ERROR_USER_NOT_LOGIN);
        }
        if (user.getLogin().contains(" ")) {
            throw new ExceptionValidationUser(VALID_ERROR_USER_LOGIN_IS_WHITESPACE);
        }
    }

    public static void checkValidFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ExceptionValidationFilm(VALID_ERROR_FILM_RELEASED_MIN);
        }
        if (film.getDuration() == null) {
            throw new ExceptionValidationFilm(VALID_ERROR_FILM_NOT_DURATION);
        }

        if (film.getDuration() < 0) {
            throw new ExceptionValidationFilm(VALID_ERROR_FILM_DURATION_MIN);
        }
    }
}
