package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmDao {

    List<Film> findAll();

    List<Film> findAll(String query, List<String> by);

    Optional<Film> findFilm(Long rowId);

    Long insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute);

    Long insert(Long rowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute);

    void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration);

    void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration);

    void deleteAll();

    void deleteAll(Long filmId);

    void deleteAll(String filmName);

    void deleteAll(LocalDate releaseDate);

    void deleteAllIsDuration(Integer duration);

    void deleteAllMpa(Integer mpaId);
}
