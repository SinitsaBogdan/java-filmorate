package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
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
