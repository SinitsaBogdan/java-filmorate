package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
import java.util.List;
import java.util.Optional;

public interface TotalGenreFilmDao {

    Optional<TotalGenreFilm> findRow(Long filmId, Integer genreId);

    Optional<List<Genre>> findAllRowsSearchFilmIdByGenreId(Long filmId);

    Optional<List<TotalGenreFilm>> findRows();

    Optional<List<TotalGenreFilm>> findRowsByFilmId(Long filmId);

    Optional<List<TotalGenreFilm>> findRowsByGenreId(Integer genreId);

    void insert(Long filmId, Integer genreId);

    void delete();

    void delete(Long filmId, Integer genreId);

    void deleteAllFilmId(Long filmId);

    void deleteAllGenreId(Integer genreId);
}
