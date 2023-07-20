package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ValidationFilmException;
import ru.yandex.practicum.filmorete.exeptions.ValidationUserException;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.*;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;

@Slf4j
@RequestMapping("/films")
@RestController
public class FilmController {

    final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public Collection<Film> findAll() {
        log.info("Запрос всех фильмов: {}", filmService.filmStorage.getFilm().size());
        return filmService.filmStorage.getFilm();
    }

    @GetMapping("/{id}")
    public Film findToId(@PathVariable Long id) {

        if (filmService.filmStorage.getCollectionsIdFilms().contains(id)) {
            return filmService.filmStorage.getFilm(id);
        } else {
            throw new ValidationFilmException(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularFilms(count);
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) throws ValidationFilmException {
        if (filmService.filmStorage.getNames().contains(film.getName())) {
            throw new ValidationFilmException(VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS);
        } else {
            validatorFilms(film);
            filmService.filmStorage.addFilm(film);
            log.info(String.format("Добавление нового фильма: %s", film.getName()));
            return film;
        }
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) throws ValidationFilmException {
        if (film.getId() == null) {
            throw new ValidationFilmException(VALID_ERROR_FILM_NOT_ID);
        }
        if (!filmService.filmStorage.getCollectionsIdFilms().contains(film.getId())) {
            throw new ValidationFilmException(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }

        validatorFilms(film);
        filmService.filmStorage.updateFilm(film);
        log.info("Обновление фильма: {}", film.getName());
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeFilm(@PathVariable Long id, @PathVariable Long userId) {

        if (
                !filmService.filmStorage.getCollectionsIdFilms().contains(id)
        ) {
            throw new ValidationFilmException(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }

        if (
                !filmService.userStorage.getCollectionsIdUsers().contains(userId)
        ) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }

        filmService.addLike(filmService.filmStorage.getFilm(id), filmService.userStorage.getUser(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeFilm(@PathVariable Long id, @PathVariable Long userId) {

        if (
                !filmService.filmStorage.getCollectionsIdFilms().contains(id)
        ) {
            throw new ValidationFilmException(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }

        if (
                !filmService.userStorage.getCollectionsIdUsers().contains(userId)
        ) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }

        filmService.removeLike(filmService.filmStorage.getFilm(id), filmService.userStorage.getUser(userId));
    }

    @DeleteMapping()
    public void clear() {
        filmService.filmStorage.clear();
        log.info("Очистка хранилища Фильмов!");
    }

    private void validatorFilms(Film film) throws ValidationFilmException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationFilmException(VALID_ERROR_FILM_RELEASED_MIN);
        }
        if (film.getDuration() == null) {
            throw new ValidationFilmException(VALID_ERROR_FILM_NOT_DURATION);
        }
        if (film.getLikeUsers() == null) {
            film.setLikeUsers(new HashSet<>());
        }
    }
}