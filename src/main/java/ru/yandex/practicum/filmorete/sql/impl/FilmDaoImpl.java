package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.time.LocalDate;
import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableFilms.*;

@Slf4j
@Component
@Primary
@Qualifier("FilmDaoImpl")
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    private final TotalGenreFilmDao totalGenreFilmDao;

    private FilmDaoImpl(JdbcTemplate jdbcTemplate, TotalGenreFilmDao totalGenreFilmDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalGenreFilmDao = totalGenreFilmDao;
    }

    @Override
    public Optional<Long> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_FILMS__LAST_ID.getTemplate()
        );
        return Optional.of(row.getLong("LAST_ID"));
    }

    @Override
    public Optional<List<Film>> findRows() {
        List<Film> result = new ArrayList<>();
        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_FILMS__ALL_ROWS.getTemplate()
        );
        while (filmsRows.next()) {
            Optional<List<Genre>> optional = totalGenreFilmDao.findAllRowsSearchFilmIdByGenreId(filmsRows.getLong("ID"));
            if (optional.isPresent()) {
                result.add(buildModel(filmsRows, optional.get()));
            } else {
                result.add(buildModel(filmsRows, new ArrayList<>()));
            }
        }
        return Optional.of(result);
    }

    @Override
    public Optional<Film> findRow(String filmName) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_FILMS__ROW_BY_NAME.getTemplate(), filmName
        );
        if (rows.next()) {
            Optional<List<Genre>> optional = totalGenreFilmDao.findAllRowsSearchFilmIdByGenreId(rows.getLong("ID"));
            return optional.map(
                    genres -> buildModel(rows, genres)
            ).or(
                    () -> Optional.of(buildModel(rows, new ArrayList<>()))
            );
        } else return Optional.empty();
    }

    @Override
    public Optional<Film> findRow(Long rowId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_FILMS__ROW_BY_ID.getTemplate(), rowId
        );

        if (rows.next()) {
            Optional<List<Genre>> optional = totalGenreFilmDao.findAllRowsSearchFilmIdByGenreId(rows.getLong("ID"));
            return optional.map(genres -> buildModel(rows, genres)).or(() -> Optional.of(buildModel(rows, new ArrayList<>())));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void insert(Long rowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute) {
        jdbcTemplate.update(
                INSERT_TABLE_FILMS_All_COLUMN.getTemplate(),
                rowId, mpaId, name, descriptions, releaseDate, durationMinute
        );
    }

    @Override
    public void insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute) {
        jdbcTemplate.update(
                INSERT_TABLE_FILMS.getTemplate(),
                mpaId, name, descriptions, releaseDate, durationMinute
        );
    }

    @Override
    public void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration) {
        jdbcTemplate.update(
                UPDATE_TABLE_FILMS__ROW_BY_ID.getTemplate(),
                mpaId, name, descriptions, releaseDate, duration, searchRowId
        );
    }

    @Override
    public void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration) {
        jdbcTemplate.update(
                UPDATE_TABLE_FILMS__ROW_BY_NAME.getTemplate(),
                mpaId, name, descriptions, releaseDate, duration, searchName
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_FILMS__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                DELETE_TABLE_FILMS__ROW_BY_ID.getTemplate(),
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                DELETE_TABLE_FILMS__ROW_BY_NAME.getTemplate(),
                name
        );
    }

    @Override
    public void delete(LocalDate releaseDate) {
        jdbcTemplate.update(
                DELETE_TABLE_FILMS__ROW_BY_RELEASE_DATE.getTemplate(),
                releaseDate
        );
    }

    @Override
    public void delete(Integer durationMinute) {
        jdbcTemplate.update(
                DELETE_TABLE_FILMS__ROW_BY_DURATION.getTemplate(),
                durationMinute
        );
    }

    @Override
    public void deleteByRating(Integer mpaId) {
        jdbcTemplate.update(
                DELETE_TABLE_FILMS__ROW_BY_MPA_ID.getTemplate(),
                mpaId
        );
    }

    protected Film buildModel(@NotNull SqlRowSet row, List<Genre> genres) {

        Mpa mpa = Mpa.builder()
                .id(row.getInt("MPA_ID"))
                .name(row.getString("MPA_NAME"))
                .build();

        return Film.builder()
                .id(row.getLong("ID"))
                .mpa(mpa)
                .genres(genres)
                .name(row.getString("NAME"))
                .description(Objects.requireNonNull(row.getString("DESCRIPTION")))
                .releaseDate(Objects.requireNonNull(row.getDate("RELEASE_DATE")).toLocalDate())
                .duration(row.getInt("DURATION"))
                .build();
    }
}
