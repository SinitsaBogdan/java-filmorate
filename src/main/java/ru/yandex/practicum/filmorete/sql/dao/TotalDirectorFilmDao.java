package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;

import java.util.List;
import java.util.Optional;

public interface TotalDirectorFilmDao {

    List<TotalDirectorFilm> findAll();

    Optional<TotalDirectorFilm> findById(Long rowId);

    List<TotalDirectorFilm> findAllTotalDirectorFilm(Long filmId);

    void insert(Long filmId, Long directorId);

    void update(TotalDirectorFilm totalDirectorFilm);

    void deleteAllFilmId(Long filmId);

    Optional<TotalDirectorFilm> findTotalDirectorFilm(Long filmId, Long dirId);

    List<Film> findPopularFilmsByDirector(Long directorId);

    List<Film> findFilmsByDirectorSortedByYear(Long directorId);

    void deleteById(Long rowId);
}
