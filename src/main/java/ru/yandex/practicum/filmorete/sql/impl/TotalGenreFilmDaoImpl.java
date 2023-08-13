package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableTotalGenreFilm.*;

@Slf4j
@Component
@Primary
@Qualifier("TotalGenreFilmDaoImpl")
public class TotalGenreFilmDaoImpl implements TotalGenreFilmDao {

    private final JdbcTemplate jdbcTemplate;

    private TotalGenreFilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<TotalGenreFilm> findRow(Long filmId, Integer genreId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_GENRE_FILM__ROW_BY_FILM_ID_AND_GENRE_ID.getTemplate(),
                filmId, genreId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Genre>> findAllRowsSearchFilmIdByGenreId(Long id) {
        List<Genre> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_NAME_GENRE_FILM__ROWS_BY_FILM_ID.getTemplate(),
                id
        );
        while (rows.next()) {
            result.add(
                    Genre.builder()
                            .id(rows.getInt("ID"))
                            .name(rows.getString("NAME"))
                            .build()
            );
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalGenreFilm>> findRows() {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_GENRE_FILM__ALL_ROWS.getTemplate()
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalGenreFilm>> findRowsByFilmId(Long filmId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_GENRE_FILM__ROWS_BY_FILM_ID.getTemplate(),
                filmId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalGenreFilm>> findRowsByGenreId(Integer genreId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_GENRE_FILM__ROWS_BY_GENRE_ID.getTemplate(),
                genreId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public void insert(Long filmId, Integer genreId) {
        jdbcTemplate.update(
                INSERT_TABLE_TOTAL_GENRE_FILM.getTemplate(),
                filmId, genreId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_GENRE_FILM__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Long filmId, Integer genreId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_GENRE_FILM__ROW_BY_FILM_ID_AND_GENRE_ID.getTemplate(),
                filmId, genreId
        );
    }

    @Override
    public void deleteAllFilmId(Long filmId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_GENRE_FILM__ROW_BY_FILM_ID.getTemplate(),
                filmId
        );
    }

    @Override
    public void deleteAllGenreId(Integer genreId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_GENRE_FILM__ROW_BY_GENRE_ID.getTemplate(),
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
