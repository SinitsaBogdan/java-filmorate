package ru.yandex.practicum.filmorate.sql.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.TotalFilmLike;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface TotalFilmLikeDao {

    List<Film> findPopularFilms(Integer limit);

    List<User> findUserToLikeFilm(Long filmId);

    List<Film> findFilmToLikeUser(Long userId);

    List<TotalFilmLike> findAllTotalFilmLike();

    List<TotalFilmLike> findAllTotalFilmLikeByFilmId(Long filmId);

    List<TotalFilmLike> findAllTotalFilmLikeByUserId(Long userId);

    List<Film> findCommonFilms(Long firstUserId, Long secondUserId);

    void insert(Long filmId, Long userId);

    void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId);

    void delete();

    void delete(Long filmId, Long userId);
}
