package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalLikeFilm;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;

public interface TotalFilmLikeDao {

    List<Film> findPopularFilms(Integer limit);

    List<Film> findPopularFilms(Integer limit, Integer genreId);

    List<Film> findPopularFilmsSortByYear(Integer limit, Integer year);

    List<User> findUserToLikeFilm(Long filmId);

    List<Film> findPopularFilms(Integer limit, Integer searchGenreId, Integer year);

    List<Film> findFilmToLikeUser(Long userId);

    List<TotalLikeFilm> findAllTotalFilmLike();

    List<TotalLikeFilm> findAllTotalFilmLikeByFilmId(Long filmId);

    List<TotalLikeFilm> findAllTotalFilmLikeByUserId(Long userId);

    List<Film> findCommonFilms(Long firstId, Long secondId);

    List<Film> getRecommendationForUser(Long userId);

    void insert(Long filmId, Long userId, Integer estimation);

    void recalculationPositive(Long filmId);

    void update(Long searchFilmId, Long searchUserId, Integer estimation);

    void delete();

    void delete(Long filmId, Long userId);
}
