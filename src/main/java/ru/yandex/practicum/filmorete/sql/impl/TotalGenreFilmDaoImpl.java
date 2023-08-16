package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.util.*;


@Slf4j
@Component
@Qualifier("TotalGenreFilmDaoImpl")
public class TotalGenreFilmDaoImpl implements TotalGenreFilmDao {

    private final JdbcTemplate jdbcTemplate;

    private TotalGenreFilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<TotalGenreFilm> findTotalGenreFilm(Long filmId, Integer genreId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM WHERE film_id = ? AND genre_id = ?;",
                filmId, genreId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public List<Genre> findAllGenreByFilmId(Long id) {
        List<Genre> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                    "FROM ROSTER_GENRE " +
                    "WHERE id IN (" +
                        "SELECT genre_id " +
                        "FROM TOTAL_GENRE_FILM " +
                        "WHERE film_id = ?" +
                    ");",
                id
        );
        while (rows.next()) result.add(
                Genre.builder()
                        .id(rows.getInt("id"))
                        .name(rows.getString("name"))
                        .build()
        );
        return result;
    }

    @Override
    public List<TotalGenreFilm> findTotalGenreFilm() {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilm(Long filmId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM WHERE film_id = ?;",
                filmId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilm(Integer genreId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM WHERE genre_id = ?;",
                genreId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public void insert(Long filmId, Integer genreId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_GENRE_FILM (film_id, genre_id) VALUES(?, ?);",
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
                "DELETE FROM TOTAL_GENRE_FILM WHERE film_id = ? AND genre_id = ?;",
                filmId, genreId
        );
    }

    @Override
    public void deleteAllFilmId(Long filmId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM WHERE film_id = ?;",
                filmId
        );
    }

    @Override
    public void deleteAllGenreId(Integer genreId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM WHERE genre_id = ?;",
                genreId
        );
    }

    protected TotalGenreFilm buildModel(@NotNull SqlRowSet row) {
        return TotalGenreFilm.builder()
                .filmId(row.getLong("film_id"))
                .genreId(row.getLong("genre_id"))
                .build();
    }
}
