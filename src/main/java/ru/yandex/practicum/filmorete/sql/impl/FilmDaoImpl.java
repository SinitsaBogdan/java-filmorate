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
                "SELECT MAX(ID) AS LAST_ID FROM FILMS;"
        );
        return Optional.of(row.getLong("LAST_ID"));
    }

    @Override
    public Optional<List<Film>> findRows() {
        List<Film> result = new ArrayList<>();
        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ROSTER_MPA.ID AS MPA_ID, " +
                        "ROSTER_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ROSTER_MPA AS ROSTER_MPA ON FILMS.MPA_ID = ROSTER_MPA.ID;"
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
                "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ROSTER_MPA.ID AS MPA_ID, " +
                        "ROSTER_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ROSTER_MPA AS ROSTER_MPA ON FILMS.MPA_ID = ROSTER_MPA.ID " +
                    "WHERE FILMS.NAME = ?;",
                filmName
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
                "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ROSTER_MPA.ID AS MPA_ID, " +
                        "ROSTER_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION " +
                        "FROM FILMS AS FILMS " +
                    "INNER JOIN ROSTER_MPA AS ROSTER_MPA ON FILMS.MPA_ID = ROSTER_MPA.ID " +
                    "WHERE FILMS.ID = ?;",
                rowId
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
                "INSERT INTO FILMS (ID, MPA_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION) " +
                    "VALUES (?, ?, ?, ?, ?, ?);",
                rowId, mpaId, name, descriptions, releaseDate, durationMinute
        );
    }

    @Override
    public void insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute) {
        jdbcTemplate.update(
                "INSERT INTO FILMS (MPA_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION) " +
                    "VALUES (?, ?, ?, ?, ?);",
                mpaId, name, descriptions, releaseDate, durationMinute
        );
    }

    @Override
    public void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration) {
        jdbcTemplate.update(
                "UPDATE FILMS " +
                    "SET " +
                        "MPA_ID = ?, " +
                        "NAME = ?, " +
                        "DESCRIPTION = ?, " +
                        "RELEASE_DATE = ?, " +
                        "DURATION = ? " +
                    "WHERE  ID = ?;",
                mpaId, name, descriptions, releaseDate, duration, searchRowId
        );
    }

    @Override
    public void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration) {
        jdbcTemplate.update(
                "UPDATE FILMS " +
                    "SET " +
                        "MPA_ID = ?, " +
                        "NAME = ?, " +
                        "DESCRIPTION = ?, " +
                        "RELEASE_DATE = ?, " +
                        "DURATION = ? " +
                    "WHERE  NAME = ?;",
                mpaId, name, descriptions, releaseDate, duration, searchName
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update("DELETE FROM FILMS;");
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE ID = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE NAME = ?;",
                name
        );
    }

    @Override
    public void delete(LocalDate releaseDate) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE RELEASE_DATE = ?;",
                releaseDate
        );
    }

    @Override
    public void delete(Integer durationMinute) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE DURATION = ?;",
                durationMinute
        );
    }

    @Override
    public void deleteByRating(Integer mpaId) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE MPA_ID = ?;",
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
