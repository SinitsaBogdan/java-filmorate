package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalFilmLike;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;

public interface TotalFilmLikeDao {

    List<Film> findPopularFilms(Integer limit);

    List<User> findUserToLikeFilm(Long filmId);

    List<Film> findFilmToLikeUser(Long userId);

    List<TotalFilmLike> findRows();

    List<TotalFilmLike> findRowsByFilmId(Long filmId);

    List<TotalFilmLike> findRowsByUserId(Long userId);

    void insert(Long filmId, Long userId);

    void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId);

    void delete();

    void delete(Long filmId, Long userId);
}
