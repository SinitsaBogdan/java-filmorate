package ru.yandex.practicum.filmorate.sql.dao;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.TotalGenreFilm;
import java.util.List;
import java.util.Optional;

public interface TotalGenreFilmDao {

    List<Genre> findAllGenreByFilmId(Long filmId);

    List<TotalGenreFilm> findTotalGenreFilm();

    List<TotalGenreFilm> findAllTotalGenreFilm(Long filmId);

    List<TotalGenreFilm> findAllTotalGenreFilm(Integer genreId);

    Optional<TotalGenreFilm> findTotalGenreFilm(Long filmId, Integer genreId);

    void insert(Long filmId, Integer genreId);

    void delete();

    void delete(Long filmId, Integer genreId);

    void deleteAllFilmId(Long filmId);

    void deleteAllGenreId(Integer genreId);
}
