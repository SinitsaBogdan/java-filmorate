package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
import java.util.List;
import java.util.Optional;

public interface TotalGenreFilmDao {

    List<Genre> findAllGenreByFilmId(Long filmId);

    List<TotalGenreFilm> findAllTotalGenreFilm();

    List<TotalGenreFilm> findAllTotalGenreFilmIsFimId(Long filmId);

    List<TotalGenreFilm> findAllTotalGenreFilmIsGenreId(Integer genreId);

    Optional<TotalGenreFilm> findTotalGenreFilm(Long filmId, Integer genreId);

    void insert(Long filmId, Integer genreId);

    void delete();

    void deleteByFilmIdAndGenreId(Long filmId, Integer genreId);

    void deleteAllFilmId(Long filmId);

    void deleteAllGenreId(Integer genreId);
}
