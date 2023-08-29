package ru.yandex.practicum.filmorete.service;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;

import org.hibernate.mapping.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;

@Service
public class ServiceDirectors {

    // private final DirectorsDao directorsDao;
    // private final TotalFilmDirectorDao totalFilmDirectorDao;

    // private ServiceDirectors(DirectorsDao directorsDao, TotalFilmDirectorDao
    // totalFilmDirectorDao) {
    // this.directorsDao = directorsDao;
    // this.totalFilmDirectorDao = totalFilmDirectorDao;
    // }

    /**
     * NEW!!!
     * Запрос режиссёра из таблицы DIRECTORS по ID [ DIRECTORS ].
     */
    public Director getDirectorSearchId(Long directorId) {
        return Director.builder().build();
    }

    /**
     * NEW!!!
     * Запрос всех режиссёров из таблицы DIRECTORS по ID [ DIRECTORS ].
     */
    public ArrayList<Director> getAllDirector() {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Добавление нового режиссёра [ DIRECTORS ].
     */
    public void add(@NotNull Director director) {
    }

    /**
     * NEW!!!
     * Обновление существующего режиссёра [ DIRECTORS ].
     */
    public void update(@NotNull Director director) {
    }

    /**
     * NEW!!!
     * Удаление всех режиссёров [ DIRECTORS ].
     */
    public void deleteAll() {
    }

    /**
     * NEW!!!
     * Удаление режиссёра по ID [ DIRECTORS ].
     */
    public void deleteSearchId(Long directorId) {
    }

    /**
     * NEW!!!
     * Запрос всех режиссёров по ID фильма [ TOTAL_FILM_DIRECTOR ].
     */
    public void getAllDirectorToFilm(@NotNull Long filmId) {
    }

    /**
     * NEW!!!
     * Запрос всех фильмов по ID режиссёра [ TOTAL_FILM_DIRECTOR ].
     */
    public void getAllFilmIsDirector(@NotNull Long directorId) {
    }

    /**
     * NEW!!!
     * Запрос всех фильмов по имени режиссёра [ TOTAL_FILM_DIRECTOR ].
     */
    public void getAllFilmIsDirector(@NotNull String directorName) {
    }

    /**
     * NEW!!!
     * Добавление нового режиссёра к сществующему фильму [ TOTAL_FILM_DIRECTOR ].
     */
    public void addDirectorToFilm(@NotNull Director director, @NotNull Long filmId) {
    }

    /**
     * NEW!!!
     * Удаление всех режиссёров из сществующего фильма по ID фильма [
     * TOTAL_FILM_DIRECTOR ].
     */
    public void deleteAllDirectorToFilm(@NotNull Long filmId) {
    }

    /**
     * NEW!!!
     * Удаление режиссёра из сществующего фильма по Имени [ TOTAL_FILM_DIRECTOR ].
     */
    public void deleteDirectorToFilm(@NotBlank String directorName, @NotNull Long filmId) {
    }

    /**
     * NEW!!!
     * Удаление режиссёра из сществующего фильма по Id [ TOTAL_FILM_DIRECTOR ].
     */
    public void deleteDirectorToFilm(@NotBlank Integer directorId, @NotNull Long filmId) {
    }

}
