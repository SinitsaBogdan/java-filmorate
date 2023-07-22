package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidationFilm;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RequestMapping("/films")
@RestController
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService filmService) {
        this.service = filmService;
    }

    @GetMapping()
    public Collection<Film> findAll() {
        return service.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film findToId(@PathVariable Long id) {
        return service.getFilm(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return service.getPopularFilms(count);
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) throws ExceptionValidationFilm {
        return service.createFilm(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) throws ExceptionValidationFilm {
        return service.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLikeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        service.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLikeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        service.removeLike(filmId, userId);
    }

    @DeleteMapping()
    public void clear() {
        service.clearStorage();
    }
}