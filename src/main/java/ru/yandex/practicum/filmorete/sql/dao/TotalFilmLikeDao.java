package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalLikeFilm;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalFilmLikeDao {

    List<TotalLikeFilm> findAll();

    List<TotalLikeFilm> findAllIsFilmId(Long filmId);

    List<TotalLikeFilm> findAllIsUserId(Long userId);

    Optional<TotalLikeFilm> findIsFilmIdAndUserId(Long filmId, Long userId);

    List<Film> findPopularIsLimit(Integer limit);

    List<Film> findPopularIsLimitAndYear(Integer limit, Integer year);

    List<Film> findPopularIsLimitAndGenre(Integer limit, Integer genreId);

    List<Film> findPopularIsLimitAndGenreAndYear(Integer limit, Integer searchGenreId, Integer year);

    List<Film> findFilmToLikeUser(Long userId);

    List<Film> findCommonFilms(Long firstId, Long secondId);

    List<Film> findRecommendationForUser(Long userId);

    List<User> findUserToLikeFilm(Long filmId);

    void insert(Long filmId, Long userId);

    void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId);

    void deleteAll();

    void deleteAll(Long filmId, Long userId);
}
