package ru.yandex.practicum.filmorete.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundGenreStorage;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorsDao;
import ru.yandex.practicum.filmorete.sql.impl.DirectorsDaoImpl;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceGenre.SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS;

@Service
public class ServiceDirectors {

    private final DirectorsDao directorsDao;

    private ServiceDirectors(DirectorsDaoImpl directorsDao) {
        this.directorsDao = directorsDao;
    }

    /**
     * NEW!!!
     * Запрос режиссёра из таблицы DIRECTORS по ID [ DIRECTORS ].
     */
    public Director getDirectorSearchId(Long directorId) {
        Optional<Director> optional = directorsDao.findById(directorId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS);
        }
    }

    /**
     * NEW!!!
     * Запрос всех режиссёров из таблицы DIRECTORS [ DIRECTORS ].
     */
    public List<Director> getAllDirector() {
        return directorsDao.findAll();
    }

    /**
     * NEW!!!
     * Добавление нового режиссёра [ DIRECTORS ].
     */
    public Director add(@NotBlank String directorName) {
        return directorsDao.insert(directorName);
    }

    /**
     * NEW!!!
     * Обновление существующего режиссёра [ DIRECTORS ].
     */
    public Director update(@NotNull Director director) {
        return directorsDao.update(director);
    }

    /**
     * NEW!!!
     * Удаление режиссёра по ID [ DIRECTORS ].
     */
    public void deleteSearchId(Long directorId) {
        directorsDao.deleteById(directorId);
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
