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
                        "f.id AS film_id, " +
                        "f.NAME AS film_name, " +
                        "f.description AS film_description, " +
                        "f.release_date AS film_release_date, " +
                        "f.duration AS film_duration, " +
                        "r.id AS mpa_id, " +
                        "r.name AS mpa_name, " +
                        "g.id AS genre_id, " +
                        "g.name AS genre_name " +
                        "FROM FILMS AS f " +
                    "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "ORDER BY f.id;"
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
    public List<Film> findCommonFilms(Long firstUserId, Long secondUserId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT f.id AS film_id, " +
                        "f.name AS film_name, " +
                        "f.description AS film_description, " +
                        "f.release_date AS film_release_date, " +
                        "f.duration AS film_duration, " +
                        "r.id AS mpa_id, " +
                        "r.name AS mpa_name, " +
                        "g.id AS genre_id, " +
                        "g.name AS genre_name " +
                    "FROM FILMS AS f " +
                    "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "WHERE f.id IN ( " +
                    "   SELECT film_id " +
                    "   FROM total_film_like " +
                    "   WHERE user_id = ? AND film_id IN ( " +
                    "       SELECT film_id " +
                    "       FROM total_film_like " +
                    "       WHERE user_id = ? " +
                    "   ) " +
                    ") " +
                    "ORDER BY f.id",
                firstUserId, secondUserId
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
                        "f.id AS film_id, " +
                        "f.name AS film_name, " +
                        "f.description AS film_description, " +
                        "f.release_date AS film_release_date, " +
                        "f.duration AS film_duration, " +
                        "r.id AS mpa_id, " +
                        "r.name AS mpa_name, " +
                        "g.id AS genre_id, " +
                        "g.name AS genre_name " +
                        "FROM FILMS AS f " +
                    "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "WHERE f.name = ? " +
                    "ORDER BY f.id;",
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
                        "f.id AS film_id, " +
                        "f.name AS film_name, " +
                        "f.description AS film_description, " +
                        "f.release_date AS film_release_date, " +
                        "f.duration AS film_duration, " +
                        "r.id AS mpa_id, " +
                        "r.name AS mpa_name, " +
                        "g.id AS genre_id, " +
                        "g.name AS genre_name " +
                    "FROM FILMS AS f " +
                    "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "WHERE f.id = ? " +
                    "ORDER BY f.id;",
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
                "INSERT INTO FILMS (id, mpa_id, name, description, release_date, duration) " +
                        "VALUES (?, ?, ?, ?, ?, ?);",
                rowId, mpaId, name, descriptions, releaseDate, durationMinute
        );
    }

    @Override
    public void insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute) {
        jdbcTemplate.update(
                "INSERT INTO FILMS (mpa_id, name, description, release_date, duration) " +
                        "VALUES (?, ?, ?, ?, ?);",
                mpaId, name, descriptions, releaseDate, durationMinute
        );
    }

    @Override
    public void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration) {
        jdbcTemplate.update(
                "UPDATE FILMS " +
                        "SET " +
                        "mpa_id = ?, " +
                        "name = ?, " +
                        "description = ?, " +
                        "release_date = ?, " +
                        "duration = ? " +
                    "WHERE id = ?;",
                mpaId, name, descriptions, releaseDate, duration, searchRowId
        );
    }

    @Override
    public void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer duration) {
        jdbcTemplate.update(
                "UPDATE FILMS " +
                        "SET " +
                        "mpa_id = ?, " +
                        "name = ?, " +
                        "description = ?, " +
                        "release_date = ?, " +
                        "duration = ? " +
                    "WHERE name = ?;",
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
                "DELETE FROM FILMS WHERE id = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE name = ?;",
                name
        );
    }

    @Override
    public void delete(LocalDate releaseDate) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE release_date = ?;",
                releaseDate
        );
    }

    @Override
    public void delete(Integer durationMinute) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE duration = ?;",
                durationMinute
        );
    }

    @Override
    public void deleteByRating(Integer mpaId) {
        jdbcTemplate.update(
                "DELETE FROM FILMS WHERE mpa_id = ?;",
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