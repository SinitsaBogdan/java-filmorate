package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionTotalFilmLikeStorage;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorete.exeptions.message.FilmErrorMessage.SERVICE_ERROR_COLLECTIONS_IN_NULL;
import static ru.yandex.practicum.filmorete.exeptions.message.TotalFilmLikeErrorMessage.SERVICE_ERROR_DOUBLE_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.message.ValidFilmErrorMessage.VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.message.UserErrorMessage.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.checkValidFilm;

@Slf4j
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
        return totalFilmLikeDao.findFilmToLikeUser(userId);
    }

    public List<Film> getCommonFilms(Long firstId, Long secondId) {
        return totalFilmLikeDao.findCommonFilms(firstId, secondId);
    }

    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        if (genreId != null && year == null) {
            return totalFilmLikeDao.findPopularIsLimitAndGenre(count, genreId);
        }
        if (genreId == null && year != null) {
            return totalFilmLikeDao.findPopularIsLimitAndYear(count, year);
        }
        if (genreId != null) {
            return totalFilmLikeDao.findPopularIsLimitAndGenreAndYear(count, genreId, year);
        }
        return totalFilmLikeDao.findPopularIsLimit(count);
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

        List<TotalGenreFilm> totalGenreFilms = totalGenreFilmDao.findAllTotalGenreFilmIsFimId(film.getId());
        if (!totalGenreFilms.isEmpty()) totalGenreFilmDao.deleteAllFilmId(film.getId());
        if (film.getGenres() != null) {
            for (Genre genreFilm : film.getGenres()) {
                Optional<TotalGenreFilm> totalGenreFilm =
                    totalGenreFilmDao.findTotalGenreFilm(film.getId(), genreFilm.getId());
                if (totalGenreFilm.isEmpty()) totalGenreFilmDao.insert(film.getId(), genreFilm.getId());
            }
        }
        return filmDao.findFilm(film.getId()).get();
    }

    public void removeFilmSearchId(@NotNull Long filmId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        filmDao.deleteAll(filmId);
    }

    public void removeLike(@NotNull Long filmId, @NotNull Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        Optional<User> optionalUser = userDao.find(userId);

        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        totalFilmLikeDao.deleteAll(filmId, userId);
        eventsDao.insert(EventType.LIKE, EventOperation.REMOVE, userId, filmId);
    }

    public void addLike(Long filmId, Long userId) {
        Optional<Film> optionalFilm = filmDao.findFilm(filmId);
        Optional<User> optionalUser = userDao.find(userId);
        Optional<TotalLikeFilm> optionalTotalLikeFilm = totalFilmLikeDao.find(filmId, userId);

        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);

        // TODO Возможная ошибка проверить бд
        if (optionalTotalLikeFilm.isEmpty()) totalFilmLikeDao.insert(filmId, userId);

        eventsDao.insert(EventType.LIKE, EventOperation.ADD, userId, filmId);
    }

    public List<Film> getFilmsToDirector(Long directorId, @NotNull String sorted) {
        List<Film> result;
        if (sorted.equals("year")) result = totalDirectorFilmDao.findFilmsByDirectorSortedByYear(directorId);
        else result = totalDirectorFilmDao.findPopularFilmsByDirector(directorId);
        if (result.size() == 0) throw new ExceptionNotFoundFilmStorage(SERVICE_ERROR_COLLECTIONS_IN_NULL);
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
