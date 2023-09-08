package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.exeptions.FilmorateException;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorete.enums.RequestPathParameter.YEAR;
import static ru.yandex.practicum.filmorete.exeptions.ResponseErrorMessage.*;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.checkValidFilm;

@Service
public class ServiceFilm {

    private final FilmDao filmDao;

    private final UserDao userDao;

    private final TotalDirectorFilmDao totalDirectorFilmDao;

    private final TotalFilmLikeDao totalFilmLikeDao;

    private final TotalGenreFilmDao totalGenreFilmDao;

    private final EventsDao eventsDao;


    @Autowired
    public ServiceFilm(
            FilmDao filmDao, UserDao userDao,
            TotalDirectorFilmDao totalDirectorFilmDao,
            TotalFilmLikeDao totalFilmLikeDao,
            TotalGenreFilmDao totalGenreFilmDao,
            EventsDao eventsDao) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.totalDirectorFilmDao = totalDirectorFilmDao;
        this.totalFilmLikeDao = totalFilmLikeDao;
        this.totalGenreFilmDao = totalGenreFilmDao;
        this.eventsDao = eventsDao;
    }

    public List<Film> getAllFilms() {
        return filmDao.findAll();
    }

    public List<Film> getFilmsToLikeUser(Long userId) {
        Optional<User> optional = userDao.findById(userId);
        if (optional.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        return totalFilmLikeDao.findFilmToLikeUser(userId);
    }

    public List<Film> getCommonFilms(Long userId, Long friendId) {
        Optional<User> optionalUser = userDao.findById(userId);
        Optional<User> optionalFriend = userDao.findById(friendId);
        if (optionalUser.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        if (optionalFriend.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        return totalFilmLikeDao.findCommonFilms(userId, friendId);
    }

    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        if (genreId != null && year == null) return totalFilmLikeDao.findPopularIsLimitAndGenre(count, genreId);
        if (genreId == null && year != null) return totalFilmLikeDao.findPopularIsLimitAndYear(count, year);
        if (genreId != null) return totalFilmLikeDao.findPopularIsLimitAndGenreAndYear(count, genreId, year);
        return totalFilmLikeDao.findPopularIsLimit(count);
    }

    public Film getFilm(Long id) {
        Optional<Film> optional = filmDao.findFilmById(id);
        if (optional.isEmpty()) throw new FilmorateException(ERROR__FILM__ID_NOT_IN_COLLECTIONS);
        return optional.get();
    }

    public Film createFilm(Film film) {
        checkValidFilm(film);

        Optional<Film> optionalFilm = filmDao.findFilmById(film.getId());
        if (optionalFilm.isPresent()) throw new FilmorateException(ERROR__FILM__DOUBLE_IN_COLLECTIONS);

        Long filmId = filmDao.insert(
                film.getMpa().getId(), film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration()
        );

        optionalFilm = filmDao.findFilmById(filmId);
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
        return filmDao.findFilmById(filmId).get();
    }

    public Film updateFilm(Film film) {
        checkValidFilm(film);
        Optional<Film> optionalFilm = filmDao.findFilmById(film.getId());
        if (optionalFilm.isEmpty()) throw new FilmorateException(ERROR__FILM__ID_NOT_IN_COLLECTIONS);
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

        List<TotalGenreFilm> totalGenreFilms = totalGenreFilmDao.findAllTotalGenreFilmIsFimId(film.getId());
        if (!totalGenreFilms.isEmpty()) totalGenreFilmDao.deleteAllFilmId(film.getId());
        if (film.getGenres() != null) {
            for (Genre genreFilm : film.getGenres()) {
                Optional<TotalGenreFilm> totalGenreFilm =
                        totalGenreFilmDao.findTotalGenreFilm(film.getId(), genreFilm.getId());
                if (totalGenreFilm.isEmpty()) totalGenreFilmDao.insert(film.getId(), genreFilm.getId());
            }
        }
        return filmDao.findFilmById(film.getId()).get();
    }

    public void removeFilmSearchId(@NotNull Long filmId) {
        Optional<Film> optionalFilm = filmDao.findFilmById(filmId);
        if (optionalFilm.isEmpty()) throw new FilmorateException(ERROR__FILM__ID_NOT_IN_COLLECTIONS);
        filmDao.deleteByFilmId(filmId);
    }

    public void removeLike(@NotNull Long filmId, @NotNull Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilmById(filmId);
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalFilm.isEmpty()) throw new FilmorateException(ERROR__FILM__ID_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        totalFilmLikeDao.deleteAll(filmId, userId);
        eventsDao.insert(EventType.LIKE, EventOperation.REMOVE, userId, filmId);
    }

    public void addLike(Long filmId, Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilmById(filmId);
        Optional<User> optionalUser = userDao.findById(userId);
        Optional<TotalLikeFilm> optionalTotalLikeFilm = totalFilmLikeDao.findIsFilmIdAndUserId(filmId, userId);
        if (optionalFilm.isEmpty()) throw new FilmorateException(ERROR__FILM__ID_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        if (optionalTotalLikeFilm.isEmpty()) totalFilmLikeDao.insert(filmId, userId);
        eventsDao.insert(EventType.LIKE, EventOperation.ADD, userId, filmId);
    }

    public List<Film> getFilmsToDirector(Long directorId, @NotNull String sorted) {
        List<Film> result;
        if (sorted.equals(YEAR.toString().toLowerCase())) {
            result = totalDirectorFilmDao.findFilmsByDirectorSortedByYear(directorId);
        } else {
            result = totalDirectorFilmDao.findPopularFilmsByDirector(directorId);
        }
        if (result.isEmpty()) throw new FilmorateException(GLOBAL_ERROR__COLLECTIONS_IS_EMPTY);
        else return result;
    }

    public void clearStorage() {
        filmDao.deleteAll();
    }

    public List<Film> getFilmsBySearchParam(String query, List<String> by) {
        List<Film> films = filmDao.findAll(query, by);
        return films.stream()
                .sorted(Comparator.comparing((Film film) -> film.getDirectors().isEmpty())
                        .thenComparing(Film::getName))
                .collect(Collectors.toList());
    }
}
