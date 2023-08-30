package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmDao {

    List<Film> findAllFilms();

    Optional<Film> findFilm(Long rowId);

    Optional<Film> findFilm(String filmName);

    Long insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute);

    Long insert(Long rowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute);

    void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration);

    void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration);

    void delete();

    void delete(Long filmId);

    void delete(String filmName);

    void delete(LocalDate releaseDate);

    void delete(Integer duration);

    void deleteByRating(Integer mpaId);

    List<Film> getFilmsBySearchParam(String query, List<String> by);
}
