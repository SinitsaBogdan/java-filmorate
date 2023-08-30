package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.util.*;

//import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.checkValidFilm;

@Slf4j
@Service
public class ServiceFilm {

    private final FilmDao filmDao;

    private final UserDao userDao;

    private final TotalDirectorFilmDao totalDirectorFilmDao;

    private final TotalFilmLikeDao totalFilmLikeDao;

    private final TotalGenreFilmDao totalGenreFilmDao;


    @Autowired
    public ServiceFilm(
            FilmDao filmDao, UserDao userDao,
            TotalDirectorFilmDao totalDirectorFilmDao,
            TotalFilmLikeDao totalFilmLikeDao,
            TotalGenreFilmDao totalGenreFilmDao
    ) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.totalDirectorFilmDao = totalDirectorFilmDao;
        this.totalFilmLikeDao = totalFilmLikeDao;
        this.totalGenreFilmDao = totalGenreFilmDao;
    }

    public List<Film> getAllFilms() {
        return filmDao.findAllFilms();
    }

    public List<Film> getFilmsToLikeUser(Long userId) {
        return totalFilmLikeDao.findFilmToLikeUser(userId);
    }

    public List<Film> getCommonFilms(Long firstId, Long secondId) {
        return totalFilmLikeDao.findCommonFilms(firstId, secondId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return totalFilmLikeDao.findPopularFilms(count);
    }

    public Film getFilm(Long id) {
        Optional<Film> optional = filmDao.findFilm(id);
        if (optional.isPresent()) return optional.get();
        else throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
    }

    public Film createFilm(Film film) {
        checkValidFilm(film);
        Long filmId = filmDao.insert(
                film.getMpa().getId(), film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration()
        );

        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                totalGenreFilmDao.insert(optionalFilm.get().getId(), genre.getId());
            }
        }

        if (film.getDirectors() != null) {
            for (Director director : film.getDirectors()) {
                totalDirectorFilmDao.insert(optionalFilm.get().getId(), director.getId());
            }
        }
        return filmDao.findFilm(filmId).get();
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

            List<TotalDirectorFilm> totalDirectorFilms = totalDirectorFilmDao.findAllTotalDirectorFilm(film.getId());
            if (!totalDirectorFilms.isEmpty()) totalDirectorFilmDao.deleteAllByFilmId(film.getId());
            if (film.getDirectors() != null) {
                for (Director director : film.getDirectors()) {
                    totalDirectorFilmDao.insert(film.getId(), director.getId());
                }
            }

        } else throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);

        List<TotalGenreFilm> totalGenreFilms = totalGenreFilmDao.findAllTotalGenreFilm(film.getId());
        if (!totalGenreFilms.isEmpty()) totalGenreFilmDao.deleteAllFilmId(film.getId());
        if (film.getGenres() != null) {
            for (Genre genreFilm : film.getGenres()) {
                Optional<TotalGenreFilm> totalGenreFilm = totalGenreFilmDao.findTotalGenreFilm(film.getId(), genreFilm.getId());
                if (totalGenreFilm.isEmpty()) totalGenreFilmDao.insert(film.getId(), genreFilm.getId());
            }
        }
        return filmDao.findFilm(film.getId()).get();
    }

    public void removeFilmSearchId(@NotNull Long filmId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        filmDao.delete(filmId);
    }

    public void removeLike(@NotNull Long filmId, @NotNull Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        Optional<User> optionalUser = userDao.findUser(userId);

        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        totalFilmLikeDao.delete(filmId, userId);
    }

    public void addLike(Long filmId, Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        Optional<User> optionalUser = userDao.findUser(userId);

        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        totalFilmLikeDao.insert(filmId, userId);
    }

    public List<Film> getFilmsToDirector(Long directorId, String sorted) {
        if (sorted.equals("year")) {
            return totalDirectorFilmDao.findFilmsByDirectorSortedByYear(directorId);
        } else {
            return totalDirectorFilmDao.findPopularFilmsByDirector(directorId);
        }
    }

    public void clearStorage() {
        filmDao.delete();
    }
}
