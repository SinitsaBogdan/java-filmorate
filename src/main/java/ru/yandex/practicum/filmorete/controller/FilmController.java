package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidationFilm;
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
     * NEW!!!
     * Запрос всех популярных фильмов с возможностью фильтрации по году и жанру.
     */
    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count,
                                      @RequestParam(defaultValue = "") Integer genreId,
                                      @RequestParam(defaultValue = "") Integer year) {
        return serviceFilms.getPopularFilms(count, genreId, year);
    }

    /**
     * Запрос фильма по id.
     */
    @GetMapping("/{filmId}")
    public Film getToId(@PathVariable Long filmId) {
        return serviceFilms.getFilm(filmId);
    }

    /**
     * Запрос списка пользователей которые поставили лайк.
     */
    @GetMapping("/{filmId}/to-like")
    public List<User> getUsersToLikeFilm(@PathVariable Long filmId) {
        return serviceUsers.getUsersToLikeFilm(filmId);
    }

    /**
     * Добавление нового фильма.
     */
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ExceptionValidationFilm {
        return serviceFilms.createFilm(film);
    }

    /**
     * Обновление существующего фильма.
     */
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ExceptionValidationFilm {
        return serviceFilms.updateFilm(film);
    }

    /**
     * Пользователь ставит лайк фильму по id.
     */
    @PostMapping("/{filmId}/like/{userId}")
    public void addEstimationFilm(@PathVariable Long filmId, @PathVariable Long userId, @RequestParam(defaultValue = "0") Integer estimation) {
        serviceFilms.addEstimation(filmId, userId, estimation);
    }

    /**
     * Удаление всех фильмов.
     */
    @DeleteMapping
    public void clear() {
        serviceFilms.clearStorage();
    }

    /**
     * Пользователь обновляет фильму по id.
     */
    @PutMapping("/{filmId}/like/{userId}")
    public void updateEstimationFilm(@PathVariable Long filmId, @PathVariable Long userId, @RequestParam Integer estimation) {
        serviceFilms.updateEstimation(filmId, userId, estimation);
    }

    /**
     * NEW!!!
     * Удалить фильм по идентификатору.
     */
    @DeleteMapping("/{filmId}")
    public void removeToId(@PathVariable Long filmId) {
        serviceFilms.removeFilmSearchId(filmId);
    }

    /**
     * Получить общие фильмы друзей, отсортированные по популярности.
     */
    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam Long userId, @RequestParam Long friendId) {
        return serviceFilms.getCommonFilms(userId, friendId);
    }

    /**
     * NEW!!!
     * Получить список фильмов режиссера,
     * отсортированных по количеству лайков или году выпуска.
     */
    @GetMapping("/director/{directorId}")
    public List<Film> getFilmsByDirectorSortedByParam(@PathVariable Long directorId, @RequestParam(defaultValue = "likes") String sortBy) {
        return serviceFilms.getFilmsToDirector(directorId, sortBy);
    }

    /**
     * NEW!!!
     * Поиск фильмов по режиссеру и/или названию.
     */
    @GetMapping("/search")
    public List<Film> getFilmsBySearchParam(@RequestParam String query, @RequestParam List<String> by) {
        return serviceFilms.getFilmsBySearchParam(query, by);
    }
}