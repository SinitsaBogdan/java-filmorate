package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorete.exeptions.*;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.StorageFilm;
import ru.yandex.practicum.filmorete.storage.StorageUser;

import java.time.LocalDate;
import java.util.HashSet;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_NOT_DURATION;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_RELEASED_MIN;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;

@Slf4j
public class ServiceValidators {

    public static void checkValidEmailContainsStorage(StorageUser storage, String email, MessageErrorValidUser message) {
        if (storage.getEmails().contains(email)) {
            throw new ExceptionValidationUser(message);
        }
    }

    public static void checkValidFilmNameContainsStorage(StorageFilm storage, String name, MessageErrorValidFilm message) {
        if (storage.getNames().contains(name)) {
            throw new ExceptionValidationFilm(message);
        }
    }

    public static void checkValidUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        if (user.getLikesFilms() == null) {
            user.setLikesFilms(new HashSet<>());
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
        if (film.getLikeUsers() == null) {
            film.setLikeUsers(new HashSet<>());
        }
    }
}
