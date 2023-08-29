package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
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

    public Film getFilm(Long id) {
        Optional<Film> optional = filmDao.findFilm(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }
    }

    public Film createFilm(Film film) {
        checkValidFilm(film);
        Optional<Film> optionalFilm;
        optionalFilm = filmDao.findFilm(film.getName());
        if (optionalFilm.isEmpty()) {
            filmDao.insert(
                    film.getMpa().getId(), film.getName(), film.getDescription(),
                    film.getReleaseDate(), film.getDuration()
            );
        } else {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS);
        }
        optionalFilm = filmDao.findFilm(film.getName());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                totalGenreFilmDao.insert(optionalFilm.get().getId(), genre.getId());
            }
        }
        return filmDao.findFilm(film.getName()).get();
    }

    public Film updateFilm(Film film) {
        checkValidFilm(film);
        Optional<Film> optionalFilm = filmDao.findFilm(film.getId());
        if (optionalFilm.isPresent()) {
            filmDao.update(
                    film.getId(), film.getMpa().getId(),
                    film.getName(), film.getDescription(),
                    film.getReleaseDate(), film.getDuration()
            );
        } else throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        List<TotalGenreFilm> totalGenreFilms = totalGenreFilmDao.findAllTotalGenreFilm(film.getId());
        if (!totalGenreFilms.isEmpty()) totalGenreFilmDao.deleteAllFilmId(film.getId());
        if (film.getGenres() != null) {
            for (Genre genreFilm : film.getGenres()) {
                Optional<TotalGenreFilm> totalGenreFilm = totalGenreFilmDao.findTotalGenreFilm(film.getId(), genreFilm.getId());
                if (totalGenreFilm.isEmpty()) {
                    totalGenreFilmDao.insert(film.getId(), genreFilm.getId());
                }
            }
        }
        return filmDao.findFilm(film.getId()).get();
    }

    public List<Film> getPopularFilms(Integer count) {
        return totalFilmLikeDao.findPopularFilms(count);
    }

    public List<Film> getAllFilms() {
        return filmDao.findAllFilms();
    }

    public List<Film> getFilmsToLikeUser(Long userId) {
        return totalFilmLikeDao.findFilmToLikeUser(userId);
    }

    public void removeLike(@NotNull Long filmId, @NotNull Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        Optional<User> optionalUser = userDao.findUser(userId);

        if (optionalFilm.isEmpty()) {
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        }
        if (optionalUser.isEmpty()) {
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }

        totalFilmLikeDao.delete(filmId, userId);
    }

    public List<Film> getCommonFilms(Long firstUserId, Long secondUserId) {
        return filmDao.findCommonFilms(firstUserId, secondUserId);
    }

    public void addLike(Long filmId, Long userId) {
        totalFilmLikeDao.insert(filmId, userId);
    }

    public void clearStorage() {
        filmDao.delete();
    }
}
