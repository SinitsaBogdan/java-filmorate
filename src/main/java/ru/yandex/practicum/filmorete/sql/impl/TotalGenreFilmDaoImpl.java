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

import static ru.yandex.practicum.filmorete.sql.requests.TotalGenreFilmRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalGenreFilmDaoImpl implements TotalGenreFilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TotalGenreFilm> findTotalGenreFilm(Long filmId, Integer genreId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_GENRE_FILM__FILM_GENRE.getSql(),
                filmId, genreId
        );
        if (row.next()) return Optional.of(FactoryModel.buildTotalGenreFilm(row));
        else return Optional.empty();
    }

    @Override
    public List<Genre> findAllGenreByFilmId(Long id) {
        List<Genre> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__GENRE__FILM.getSql(),
                id
        );
        while (row.next()) result.add(FactoryModel.buildGenre(row));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findTotalGenreFilm() {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_GENRE_FILM.getSql()
        );
        while (row.next()) result.add(FactoryModel.buildTotalGenreFilm(row));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilmIsFimId(Long filmId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_GENRE_FILM__FILM.getSql(),
                filmId
        );
        while (row.next()) result.add(FactoryModel.buildTotalGenreFilm(row));
        return result;
    }

    @Override
    public List<TotalGenreFilm> findAllTotalGenreFilmIsGenreId(Integer genreId) {
        List<TotalGenreFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_GENRE_FILM__GENRE.getSql(),
                genreId
        );
        while (row.next()) result.add(FactoryModel.buildTotalGenreFilm(row));
        return result;
    }

    @Override
    public void insert(Long filmId, Integer genreId) {
        jdbcTemplate.update(INSERT_ONE__TOTAL_GENRE_FILM__FILM_GENRE.getSql(), filmId, genreId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(DELETE_ALL__TOTAL_GENRE_FILM.getSql()
        );
    }

    @Override
    public void delete(Long filmId, Integer genreId) {
        jdbcTemplate.update(DELETE_ONE__TOTAL_GENRE_FILM__FILM_GENRE.getSql(), filmId, genreId
        );
    }

    @Override
    public void deleteAllFilmId(Long filmId) {
        jdbcTemplate.update(DELETE_ONE__TOTAL_GENRE_FILM__FILM.getSql(), filmId
        );
    }

    @Override
    public void deleteAllGenreId(Integer genreId) {
        jdbcTemplate.update(DELETE_ONE__TOTAL_GENRE_FILM__GENRE.getSql(), genreId
        );
    }
}
