package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.model.Film;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@Component
@Qualifier("FilmDaoImpl")
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    private FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> findAllFilms() {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "FILMS.ID AS FILM_ID, " +
                        "FILMS.NAME AS FILM_NAME, " +
                        "FILMS.DESCRIPTION AS FILM_DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS FILM_RELEASE_DATE, " +
                        "FILMS.DURATION AS FILM_DURATION, " +
                        "R.ID AS MPA_ID, " +
                        "R.NAME AS MPA_NAME, " +
                        "G.ID AS GENRE_ID, " +
                        "G.NAME AS GENRE_NAME, " +
                    "FROM FILMS " +
                    "LEFT JOIN ROSTER_MPA AS R ON FILMS.MPA_ID = R.ID " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS T ON FILMS.ID=T.FILM_ID " +
                    "LEFT JOIN ROSTER_GENRE AS G ON T.GENRE_ID=G.ID " +
                    "ORDER BY FILMS.ID;"
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(filmId)) {
                Film film = buildModel(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
        }

        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<Film> findFilm(String filmName) {
        Map<String, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "FILMS.ID AS FILM_ID, " +
                        "FILMS.NAME AS FILM_NAME, " +
                        "FILMS.DESCRIPTION AS FILM_DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS FILM_RELEASE_DATE, " +
                        "FILMS.DURATION AS FILM_DURATION, " +
                        "R.ID AS MPA_ID, " +
                        "R.NAME AS MPA_NAME, " +
                        "G.ID AS GENRE_ID, " +
                        "G.NAME AS GENRE_NAME, " +
                    "FROM FILMS " +
                    "LEFT JOIN ROSTER_MPA AS R ON FILMS.MPA_ID = R.ID " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS T ON FILMS.ID=T.FILM_ID " +
                    "LEFT JOIN ROSTER_GENRE AS G ON T.GENRE_ID=G.ID " +
                    "WHERE FILMS.NAME = ? " +
                    "ORDER BY FILMS.ID;",
                filmName
        );
        while (rows.next()) {
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(filmName)) {
                Film film = buildModel(rows);
                result.put(filmName, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmName).addGenre(genre);
            }
        }
        return Optional.ofNullable(result.get(filmName));
    }

    @Override
    public Optional<Film> findFilm(Long rowId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "FILMS.ID AS FILM_ID, " +
                        "FILMS.NAME AS FILM_NAME, " +
                        "FILMS.DESCRIPTION AS FILM_DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS FILM_RELEASE_DATE, " +
                        "FILMS.DURATION AS FILM_DURATION, " +
                        "R.ID AS MPA_ID, " +
                        "R.NAME AS MPA_NAME, " +
                        "G.ID AS GENRE_ID, " +
                        "G.NAME AS GENRE_NAME, " +
                    "FROM FILMS " +
                    "LEFT JOIN ROSTER_MPA AS R ON FILMS.MPA_ID = R.ID " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS T ON FILMS.ID=T.FILM_ID " +
                    "LEFT JOIN ROSTER_GENRE AS G ON T.GENRE_ID=G.ID " +
                    "WHERE FILMS.ID = ? " +
                    "ORDER BY FILMS.ID;",
                rowId
        );
        while (rows.next()) {
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(rowId)) {
                Film film = buildModel(rows);
                result.put(rowId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(rowId).addGenre(genre);
            }
        }
        return Optional.ofNullable(result.get(rowId));
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

    protected Film buildModel(@NotNull SqlRowSet row) {
        Mpa mpa = Mpa.builder()
                .id(row.getInt("MPA_ID"))
                .name(row.getString("MPA_NAME"))
                .build();

        return Film.builder()
                .id(row.getLong("FILM_ID"))
                .mpa(mpa)
                .name(row.getString("FILM_NAME"))
                .description(Objects.requireNonNull(row.getString("FILM_DESCRIPTION")))
                .releaseDate(Objects.requireNonNull(row.getDate("FILM_RELEASE_DATE")).toLocalDate())
                .duration(row.getInt("FILM_DURATION"))
                .build();
    }
}
