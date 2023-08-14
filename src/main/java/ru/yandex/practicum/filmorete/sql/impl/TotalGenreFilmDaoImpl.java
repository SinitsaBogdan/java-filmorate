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
                "SELECT * FROM TOTAL_GENRE_FILM WHERE FILM_ID = ? AND GENRE_ID = ?;",
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
                    "WHERE ID IN (" +
                        "SELECT GENRE_ID " +
                        "FROM TOTAL_GENRE_FILM " +
                        "WHERE FILM_ID = ?" +
                    ");",
                id
        );
        while (rows.next()) result.add(
                Genre.builder()
                        .id(rows.getInt("ID"))
                        .name(rows.getString("NAME"))
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
                "SELECT * FROM TOTAL_GENRE_FILM WHERE FILM_ID = ?;",
                filmId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilm(Integer genreId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_GENRE_FILM WHERE GENRE_ID = ?;",
                genreId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public void insert(Long filmId, Integer genreId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_GENRE_FILM (FILM_ID, GENRE_ID) VALUES(?, ?);",
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
                "DELETE FROM TOTAL_GENRE_FILM WHERE FILM_ID = ? AND GENRE_ID = ?;",
                filmId, genreId
        );
    }

    @Override
    public void deleteAllFilmId(Long filmId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM WHERE FILM_ID = ?;",
                filmId
        );
    }

    @Override
    public void deleteAllGenreId(Integer genreId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_GENRE_FILM WHERE GENRE_ID = ?;",
                genreId
        );
    }

    protected TotalGenreFilm buildModel(@NotNull SqlRowSet row) {
        return TotalGenreFilm.builder()
                .filmId(row.getLong("FILM_ID"))
                .genreId(row.getLong("GENRE_ID"))
                .build();
    }
}
