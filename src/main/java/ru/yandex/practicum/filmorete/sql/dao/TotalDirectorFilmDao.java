package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;

import java.util.List;

public interface TotalDirectorFilmDao {

    List<TotalDirectorFilm> findAll();

    List<TotalDirectorFilm> findById(Long directorId);

    List<TotalDirectorFilm> findAllTotalDirectorFilm(Long filmId);

    void insert(Long filmId, Long directorId);

    void update(Long filmId, Long directorId);

    void deleteAllByFilmId(Long filmId);

    void delete();

    List<Film> findPopularFilmsByDirector(Long directorId);

    List<Film> findFilmsByDirectorSortedByYear(Long directorId);
}
