package ru.yandex.practicum.filmorete.service;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundDirectorStorage;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalDirectorFilmDao;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceDirector.SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS;

@Service
public class ServiceDirector {

     private final DirectorDao directorDao;
     private final TotalDirectorFilmDao totalFilmDirectorDao;

     private ServiceDirector(DirectorDao directorsDao, TotalDirectorFilmDao totalFilmDirectorDao) {
         this.directorDao = directorsDao;
         this.totalFilmDirectorDao = totalFilmDirectorDao;
     }

    /**
     * Запрос режиссёра из таблицы DIRECTORS по ID [ DIRECTORS ].
     */
    public Director getDirectorSearchId(Long directorId) {
        Optional<Director> result = directorDao.findById(directorId);
        if (result.isPresent()) return result.get();
        else throw new ExceptionNotFoundDirectorStorage(SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
    }

    /**
     * Запрос всех режиссёров из таблицы DIRECTORS [ DIRECTORS ].
     */
    public List<Director> getAllDirector() {
        return directorDao.findAll();
    }

    /**
     * Добавление нового режиссёра [ DIRECTORS ].
     */
    public Director add(@NotNull Director director) {
        Long id = directorDao.insert(director.getName());
        Optional<Director> result = directorDao.findById(id);
        return result.orElse(null);
    }

    /**
     * Обновление существующего режиссёра [ DIRECTORS ].
     */
    public Director update(@NotNull Director director) {
        Optional<Director> optionalDirector = directorDao.findById(director.getId());
        if (optionalDirector.isPresent()) {
            directorDao.update(director.getId(), director.getName());
            Optional<Director> result = directorDao.findById(director.getId());
            return result.orElse(null);
        } else throw new ExceptionNotFoundDirectorStorage(SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
    }

    /**
     * Удаление всех режиссёров [ DIRECTORS ].
     */
    public void deleteAll() {
        directorDao.delete();
    }

    /**
     * Удаление режиссёра по ID [ DIRECTORS ].
     */
    public void deleteSearchId(Long directorId) {
        directorDao.delete(directorId);
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
