package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.util.*;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.checkValidFilm;

@Slf4j
@Service
public class ServiceFilm {

    private final FilmDao filmDao;
    private final UserDao userDao;
    private final TotalFilmLikeDao totalFilmLikeDao;
    private final TotalGenreFilmDao totalGenreFilmDao;

    @Autowired
    public ServiceFilm(FilmDao filmDao, UserDao userDao, TotalFilmLikeDao totalFilmLikeDao, TotalGenreFilmDao totalGenreFilmDao) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.totalFilmLikeDao = totalFilmLikeDao;
        this.totalGenreFilmDao = totalGenreFilmDao;
    }

    public Film createFilm(Film film) {
        checkValidFilm(film);
        Optional<Film> optionalFilm;
        optionalFilm = filmDao.findRow(film.getName());
        if (optionalFilm.isEmpty()) {
            filmDao.insert(
                    film.getMpa().getId(), film.getName(), film.getDescription(),
                    film.getReleaseDate(), film.getDuration()
            );
        } else {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS);
        }
        optionalFilm = filmDao.findRow(film.getName());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                totalGenreFilmDao.insert(optionalFilm.get().getId(), genre.getId());
            }
        }
        return filmDao.findRow(film.getName()).get();
    }

    public Film updateFilm(Film film) {
        checkValidFilm(film);
        Optional<Film> optional = filmDao.findRow(film.getId());
        if (optional.isEmpty()) {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }
        filmDao.update(film.getId(), film.getMpa().getId(), film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration()
        );
        totalGenreFilmDao.deleteAllFilmId(film.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (totalGenreFilmDao.findRow(film.getId(), genre.getId()).isEmpty()) {
                    totalGenreFilmDao.insert(film.getId(), genre.getId());
                }
            }
        }
        return filmDao.findRow(film.getId()).get();
    }

    public List<Film> getPopularFilms(Integer count) {
        Optional<List<Film>> optional = totalFilmLikeDao.findPopularFilms(count);
        return optional.orElse(null);
    }

    public List<Film> getAllFilms() {
        Optional<List<Film>> optional = filmDao.findRows();
        return optional.orElse(null);
    }

    public Film getFilm(Long id) {
        Optional<Film> optional = filmDao.findRow(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }
    }

    public List<Film> getFilmsToLikeUser(Long userId) {
        Optional<List<Film>> optional = totalFilmLikeDao.findFilmToLikeUser(userId);
        return optional.orElse(null);
    }

    public void removeLike(@NotNull Long filmId, @NotNull Long userId) {
        Optional<Film> optionalFilm = filmDao.findRow(filmId);
        Optional<User> optionalUser = userDao.findRow(userId);

        if (optionalFilm.isEmpty()) {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }
        if (optionalUser.isEmpty()) {
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }

        totalFilmLikeDao.delete(filmId, userId);
    }

    public void addLike(Long filmId, Long userId) {
        totalFilmLikeDao.insert(filmId, userId);
    }

    public void clearStorage() {
        filmDao.delete();
    }

    public void removeFilm(Long filmId) {
        filmDao.delete(filmId);
    }
}
