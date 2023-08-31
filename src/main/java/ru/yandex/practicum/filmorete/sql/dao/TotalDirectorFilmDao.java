package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;

import java.util.List;
import java.util.Optional;

public interface TotalDirectorFilmDao {

    List<TotalDirectorFilm> findAll();

    Optional<TotalDirectorFilm> findById(Long rowId);

    List<TotalDirectorFilm> findAllTotalDirectorFilm(Long filmId);

    void insert(Long filmId, Long directorId);

    void update(Long filmId, Long directorId);

    void delete();

    void deleteAllByFilmId(Long filmId);

    List<Film> findPopularFilmsByDirector(Long directorId);

    List<Film> findFilmsByDirectorSortedByYear(Long directorId);

    void deleteAllByDirectorId(Long directorId);
}
