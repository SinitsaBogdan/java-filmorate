package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.comparators.ComparatorUserToSeizeLike;
import ru.yandex.practicum.filmorete.exeptions.ExceptionServiceFilmorate;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.StorageFilm;
import ru.yandex.practicum.filmorete.storage.StorageUser;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceFilmore.SERVICE_ERROR_POPULAR_FILM_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.*;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.*;

@Slf4j
@Service
public class ServiceFilm {

    private final StorageFilm filmStorage;
    private final StorageUser userStorage;

    @Autowired
    public ServiceFilm(StorageFilm filmStorage, StorageUser userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film createFilm(Film film) {
        checkValidFilmNameContainsStorage(filmStorage, film.getName(), VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS);
        checkValidFilm(film);
        filmStorage.addFilm(film);
        log.info(String.format("Добавление нового фильма: %s", film.getName()));
        return film;
    }

    public Film updateFilm(Film film) {
        checkValidFilm(film);
        filmStorage.updateFilm(film);
        log.info("Обновление фильма: {}", film.getName());
        return film;
    }

    public void removeFilm(Long filmId) {
        filmStorage.removeFilm(filmId);
    }

    public Collection<Film> getAllFilms() {
        log.info("Запрос всех фильмов: {}", filmStorage.getFilm().size());
        return filmStorage.getFilm();
    }

    public Film getFilm(Long id) {
        return filmStorage.getFilm(id);
    }

    public List<Film> getFilmsLikesToUser(@NotNull User user) {
        return user.getLikesFilms().stream()
                .filter(Objects::nonNull)
                .map(filmStorage::getFilm)
                .collect(Collectors.toList());

    }

    public List<User> getUserLikesToFilm(@NotNull Film film) {
        return film.getLikeUsers().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<Film> getPopularFilms(Integer count) {
        try {
            List<Film> list = new ArrayList<>(filmStorage.getFilm());
            list.sort(new ComparatorUserToSeizeLike().reversed());

            if (count > filmStorage.getNames().size()) {
                count = filmStorage.getNames().size();
            }
            return list.subList(0, count);
        } catch (Exception e) {
            throw new ExceptionServiceFilmorate(SERVICE_ERROR_POPULAR_FILM_NOT_IN_COLLECTIONS);
        }
    }

    public void removeLike(@NotNull Long filmId, @NotNull Long userId) {

        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);

        if (user.getLikesFilms().contains(film.getId())) {
            user.removeLikes(film);
        }
        if (film.getLikeUsers().contains(user.getId())) {
            film.removeLike(user);
        }
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);

        checkValidUser(user);
        checkValidFilm(film);

        if (!user.getLikesFilms().contains(film.getId())) {
            user.addLike(film);
        }
        if (!film.getLikeUsers().contains(user.getId())) {
            film.addLike(user);
        }
    }

    public void clearStorage() {
        log.info("Очистка хранилища Фильмов!");
        filmStorage.clear();
    }
}
