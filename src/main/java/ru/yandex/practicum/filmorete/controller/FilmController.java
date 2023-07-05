package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ValidationFilmException;
import ru.yandex.practicum.filmorete.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.*;

@Slf4j
@RequestMapping("/films")
@RestController
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private final Set<String> names = new HashSet<>();
    private Integer lastIdentification = 1;

    @GetMapping()
    public Collection<Film> findAll() {
        log.info("Запрос всех фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) throws ValidationFilmException {
        if (names.contains(film.getName())) {
            throw new ValidationFilmException(VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS, 400);
        } else {
            validatorFilms(film);
            film.setId(getLastIdentification());
            films.put(film.getId(), film);
            names.add(film.getName());
            log.info(String.format("Добавление нового фильма: %s", film.getName()));
            return film;
        }
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) throws ValidationFilmException {
        if (film.getId() == null) {
            throw new ValidationFilmException(VALID_ERROR_FILM_NOT_ID, 400);
        }
        if (!films.containsKey(film.getId())) {
            throw new ValidationFilmException(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS, 500);
        }

        validatorFilms(film);
        Film oldFilm = films.get(film.getId());
        names.remove(oldFilm.getName());
        names.add(film.getName());

        films.put(film.getId(), film);
        log.info("Обновление фильма: {}", film.getName());
        return film;
    }

    @DeleteMapping()
    public void clear() {
        films.clear();
        names.clear();
        lastIdentification = 1;
        log.info("Очистка хранилища Фильмов!");
    }

    private void validatorFilms(Film film) throws ValidationFilmException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationFilmException(VALID_ERROR_FILM_RELEASED_MIN, 400);
        }
        if (film.getDuration() == null) {
            throw new ValidationFilmException(VALID_ERROR_FILM_NOT_DURATION, 400);
        }
    }

    private Integer getLastIdentification() {
        return lastIdentification++;
    }
}