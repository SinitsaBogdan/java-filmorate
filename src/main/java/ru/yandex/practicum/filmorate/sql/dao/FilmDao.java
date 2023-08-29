package ru.yandex.practicum.filmorate.sql.dao;

import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmDao {

    List<Film> findAllFilms();

    Optional<Film> findFilm(Long rowId);

    Optional<Film> findFilm(String filmName);

    void insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute);

    void insert(Long rowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute);

    void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration);

    void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration);

    void delete();

    void delete(Long rowId);

    void delete(String name);

    void delete(LocalDate releaseDate);

    void delete(Integer duration);

    void deleteByRating(Integer mpaId);
}
