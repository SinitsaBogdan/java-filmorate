package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidation;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.service.ServiceFilm;
import ru.yandex.practicum.filmorete.service.ServiceUser;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RequestMapping("/films")
@RestController
public class FilmController {

    private final ServiceFilm serviceFilms;

    private final ServiceUser serviceUsers;

    private FilmController(ServiceFilm serviceFilms, ServiceUser serviceUsers) {
        this.serviceFilms = serviceFilms;
        this.serviceUsers = serviceUsers;
    }

    /**
     * Запрос всех фильмов.
     */
    @GetMapping
    public List<Film> findAll() {
        return serviceFilms.getAllFilms();
    }

    /**
     * Добавление нового фильма.
     */
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ExceptionValidation {
        return serviceFilms.createFilm(film);
    }

    /**
     * Обновление существующего фильма.
     */
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ExceptionValidation {
        return serviceFilms.updateFilm(film);
    }

    /**
     * Удаление всех фильмов.
     */
    @DeleteMapping
    public void clear() {
        serviceFilms.clearStorage();
    }

    /**
     * Запрос всех популярных фильмов с возможностью фильтрации по году и жанру.
     */
    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(defaultValue = "10") Integer count,
            @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) Integer year) {
        return serviceFilms.getPopularFilms(count, genreId, year);
    }

    /**
     * Получить общие фильмы друзей, отсортированные по популярности.
     */
    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam Long userId, @RequestParam Long friendId) {
        return serviceFilms.getCommonFilms(userId, friendId);
    }

    /**
     * Поиск фильмов по режиссеру и/или названию.
     */
    @GetMapping("/search")
    public List<Film> getFilmsBySearchParam(
            @RequestParam String query,
            @RequestParam List<String> by
    ) {
        return serviceFilms.getFilmsBySearchParam(query, by);
    }

    /**
     * Запрос фильма по id.
     */
    @GetMapping("/{filmId}")
    public Film getToId(@PathVariable Long filmId) {
        return serviceFilms.getFilm(filmId);
    }

    /**
     * Удалить фильм по идентификатору.
     */
    @DeleteMapping("/{filmId}")
    public void removeToId(@PathVariable Long filmId) {
        serviceFilms.removeFilmSearchId(filmId);
    }

    /**
     * Запрос списка пользователей которые поставили лайк.
     */
    @GetMapping("/{filmId}/to-like")
    public List<User> getUsersToLikeFilm(@PathVariable Long filmId) {
        return serviceUsers.getUsersToLikeFilm(filmId);
    }

    /**
     * Пользователь ставит лайк фильму по id.
     */
    @PutMapping("/{filmId}/like/{userId}")
    public void addLikeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        serviceFilms.addLike(filmId, userId);
    }

    /**
     * Пользователь удаляет лайк фильму по id.
     */
    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLikeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        serviceFilms.removeLike(filmId, userId);
    }

    /**
     * Получить список фильмов режиссера,
     * отсортированных по количеству лайков или году выпуска.
     */
    @GetMapping("/director/{directorId}")
    public List<Film> getFilmsByDirectorSortedByParam(
            @PathVariable Long directorId,
            @RequestParam(defaultValue = "likes") String sortBy
    ) {
        return serviceFilms.getFilmsToDirector(directorId, sortBy);
    }
}