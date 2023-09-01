package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalGenreFilmDaoImpl implements TotalGenreFilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TotalGenreFilm> findTotalGenreFilm(Long filmId, Integer genreId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM " +
                    "WHERE film_id = ? AND genre_id = ?;",
                filmId, genreId
        );
        if (row.next()) return Optional.of(FactoryModel.buildTotalGenreFilm(row));
        else return Optional.empty();
    }

    @Override
    public List<Genre> findAllGenreByFilmId(Long id) {
        List<Genre> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                    "FROM ROSTER_GENRE " +
                    "WHERE id IN (" +
                        "SELECT genre_id " +
                        "FROM TOTAL_GENRE_FILM " +
                        "WHERE film_id = ?" +
                    ");",
                id
        );
        while (row.next()) result.add(FactoryModel.buildGenre(row));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findTotalGenreFilm() {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM;"
        );
        while (row.next()) result.add(FactoryModel.buildTotalGenreFilm(row));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilm(Long filmId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                    "FROM TOTAL_GENRE_FILM " +
                    "WHERE film_id = ?;",
                filmId
        );
        while (row.next()) result.add(FactoryModel.buildTotalGenreFilm(row));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilm(Integer genreId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                    "FROM TOTAL_GENRE_FILM " +
                    "WHERE genre_id = ?;",
                genreId
        );
        while (row.next()) result.add(FactoryModel.buildTotalGenreFilm(row));
        return result;
    }

    @Override
    public void insert(Long filmId, Integer genreId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_GENRE_FILM (film_id, genre_id) " +
                    "VALUES(?, ?);",
                filmId, genreId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM;"
        );
    }

    @Override
    public void delete(Long filmId, Integer genreId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM " +
                    "WHERE film_id = ? AND genre_id = ?;",
                filmId, genreId
        );
    }

    @Override
    public void deleteAllFilmId(Long filmId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM " +
                    "WHERE film_id = ?;",
                filmId
        );
    }

    @Override
    public void deleteAllGenreId(Integer genreId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM " +
                    "WHERE genre_id = ?;",
                genreId
        );
    }
}
