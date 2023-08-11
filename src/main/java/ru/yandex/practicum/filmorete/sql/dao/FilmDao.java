package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Film;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmDao {

    Optional<Long> findLastId();

    Optional<List<Film>> findRows();

    Optional<Film> findRow(Long rowId);

    Optional<Film> findRow(String filmName);

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
