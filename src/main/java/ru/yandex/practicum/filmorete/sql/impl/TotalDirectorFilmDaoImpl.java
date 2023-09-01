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
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalDirectorFilmDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalDirectorFilmDaoImpl implements TotalDirectorFilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TotalDirectorFilm> findAll() {
        List<TotalDirectorFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT film_id, director_id " +
                    "FROM TOTAL_FILM_DIRECTOR;"
        );
        while (row.next()) result.add(FactoryModel.buildTotalDirectorFilm(row));
        return result;
    }

    @Override
    public List<TotalDirectorFilm> findAllTotalDirectorFilm(Long filmId) {
        List<TotalDirectorFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_DIRECTOR " +
                    "WHERE film_id = ?;",
                filmId
        );
        while (row.next()) result.add(FactoryModel.buildTotalDirectorFilm(row));
        return result;
    }

    @Override
    public void insert(Long filmId, Long directorId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_FILM_DIRECTOR (film_id, director_id) " +
                    "VALUES (?, ?);",
                filmId, directorId
        );
    }

    @Override
    public void update(Long filmId, Long directorId) {
        jdbcTemplate.update(
                "UPDATE TOTAL_FILM_DIRECTOR " +
                    "SET film_id = ? " +
                    "WHERE director_id = ?;",
                filmId, directorId
        );
    }

    @Override
    public void deleteAllByFilmId(Long filmId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_FILM_DIRECTOR " +
                    "WHERE film_id = ?;",
                filmId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
            "DELETE FROM TOTAL_FILM_DIRECTOR;"
        );
    }

    @Override
    public List<Film> findPopularFilmsByDirector(Long directorId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(generateSqlRequest() + "WHERE f.id IN ( SELECT film_id FROM " +
                "TOTAL_FILM_DIRECTOR WHERE director_id = ?) " +
                "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC;", directorId);
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
    public List<Film> findFilmsByDirectorSortedByYear(Long directorId) {
        List<Film> result = new ArrayList<>();
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
                        "d.name AS director_name, " +
                        "EXTRACT(YEAR FROM f.release_date) AS release_year, " +
                        "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) AS size_like " +
                    "FROM FILMS AS f " +
                    "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                    "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                    "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
                    "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
                    "WHERE f.id IN ( " +
                        "SELECT film_id FROM " +
                        "TOTAL_FILM_DIRECTOR WHERE director_id = ?" +
                    ") " +
                    "ORDER BY EXTRACT(YEAR FROM f.release_date) ASC;",
                directorId
        );
        while (row.next()) {
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");
            Film film = FactoryModel.buildFilm(row);
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                film.addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                film.addDirector(director);
            }
            result.add(film);
        }
        return result;
    }

    private String generateSqlRequest() {
        return "SELECT " +
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
                    "d.name AS director_name, " +
                    "EXTRACT(YEAR FROM f.release_date) AS release_year, " +
                    "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) AS size_like " +
                "FROM FILMS AS f " +
                "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
                "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id ";
    }
}
