package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> getFilmsBySearchParam(String query, List<String> by) {
        String sql = "SELECT " +
                "f.id AS film_id, " +
                "f.NAME AS film_name, " +
                "f.description AS film_description, " +
                "f.release_date AS film_release_date, " +
                "f.duration AS film_duration, " +
                "r.id AS mpa_id, " +
                "r.name AS mpa_name, " +
                "g.id AS genre_id, " +
                "g.name AS genre_name, " +
                "d.id AS director_id, " +
                "d.name AS director_name " +
                "FROM FILMS AS f " +
                "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
                "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id ";

        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append("WHERE ");
        List<String> conditions = new ArrayList<>();

        if (by.contains("director") && by.contains("title")) {
            conditions.add("(f.name ILIKE '%" + query + "%' OR d.name ILIKE '%" + query + "%')");
        } else if (by.contains("director")) {
            conditions.add("d.name ILIKE '%" + query + "%'");
        } else if (by.contains("title")) {
            conditions.add("f.name ILIKE '%" + query + "%'");
        }
        if (!conditions.isEmpty()) {
            sqlBuilder.append(String.join(" OR ", conditions));
        } else {
            return Collections.emptyList();
        }

        sqlBuilder.append(" ORDER BY f.id");
        SqlRowSet row = jdbcTemplate.queryForRowSet(sqlBuilder.toString());

        Map<Long, Film> result = new HashMap<>();
        while (row.next()) {
            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long directorId = row.getLong("DIRECTOR_ID");
            String directorName = row.getString("DIRECTOR_NAME");

            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(row);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (directorName != null) {
                Director director = Director.builder().id(directorId).name(directorName).build();
                result.get(filmId).addDirector(director);
            }
        }

        return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findAllFilms() {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "f.id AS film_id, " +
                        "f.NAME AS film_name, " +
                        "f.description AS film_description, " +
                        "f.release_date AS film_release_date, " +
                        "f.duration AS film_duration, " +
                        "r.id AS mpa_id, " +
                        "r.name AS mpa_name, " +
                        "g.id AS genre_id, " +
                        "g.name AS genre_name, " +
                        "d.id AS director_id, " +
                        "d.name AS director_name " +
                    "FROM FILMS AS f " +
                    "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
                    "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
                    "ORDER BY f.id;"
        );
        while (row.next()) {
            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(row);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(filmId).addDirector(director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<Film> findFilm(Long rowId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "f.id AS film_id, " +
                        "f.name AS film_name, " +
                        "f.description AS film_description, " +
                        "f.release_date AS film_release_date, " +
                        "f.duration AS film_duration, " +
                        "r.id AS mpa_id, " +
                        "r.name AS mpa_name, " +
                        "g.id AS genre_id, " +
                        "g.name AS genre_name, " +
                        "d.id AS director_id, " +
                        "d.name AS director_name " +
                    "FROM FILMS AS f " +
                    "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
                    "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
                    "WHERE f.id = ? " +
                    "ORDER BY f.id;",
            rowId
        );
        while (row.next()) {
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");
            if (!result.containsKey(rowId)) {
                Film film = FactoryModel.buildFilm(row);
                result.put(rowId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(rowId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(rowId).addDirector(director);
            }
        }
        return Optional.ofNullable(result.get(rowId));
    }

    @Override
    public Long insert(Long rowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate,
                       Integer durationMinute) {
        jdbcTemplate.update(
            "INSERT INTO FILMS (id, mpa_id, name, description, release_date, duration) " +
                "VALUES (?, ?, ?, ?, ?, ?);",
            rowId, mpaId, name, descriptions, releaseDate, durationMinute
        );
        return jdbcTemplate.queryForObject(
            "SELECT MAX(ID) AS id FROM FILMS;", Long.class
        );
    }

    @Override
    public Long insert(Integer mpaId, String name, String descriptions, LocalDate releaseDate, Integer durationMinute) {
        jdbcTemplate.update(
            "INSERT INTO FILMS (mpa_id, name, description, release_date, duration) " +
                "VALUES (?, ?, ?, ?, ?);",
            mpaId, name, descriptions, releaseDate, durationMinute
        );
        return jdbcTemplate.queryForObject(
            "SELECT MAX(ID) AS id FROM FILMS;", Long.class
        );
    }

    @Override
    public void update(Long searchRowId, Integer mpaId, String name, String descriptions, LocalDate releaseDate,
                       Integer duration) {
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
    public void update(String searchName, Integer mpaId, String name, String descriptions, LocalDate releaseDate,
                       Integer duration) {
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
    public void delete(Long filmId) {
        jdbcTemplate.update(
            "DELETE FROM FILMS WHERE id = ?;",
            filmId
        );
    }

    @Override
    public void delete(String filmName) {
        jdbcTemplate.update(
            "DELETE FROM FILMS WHERE name = ?;",
            filmName
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
}