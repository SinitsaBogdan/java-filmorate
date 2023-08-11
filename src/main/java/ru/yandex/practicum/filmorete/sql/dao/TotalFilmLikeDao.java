package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalFilmLike;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalFilmLikeDao {

    Optional<List<Film>> findPopularFilms(Integer limit);

    Optional<List<User>> findUserToLikeFilm(Long filmId);

    Optional<List<Film>> findFilmToLikeUser(Long userId);

    Optional<List<TotalFilmLike>> findRows();

    Optional<List<TotalFilmLike>> findRowsByFilmId(Long filmId);

    Optional<List<TotalFilmLike>> findRowsByUserId(Long userId);

    void insert(Long filmId, Long userId);

    void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId);

    void delete();

    void delete(Long filmId, Long userId);
}
