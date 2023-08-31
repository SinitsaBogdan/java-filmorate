package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
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

    private final FilmDaoImpl filmDao;

    @Override
    public List<TotalDirectorFilm> findAll() {
        Map<Long, TotalDirectorFilm> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "tf.film_id AS filmId, " +
                        "tf.director_id AS directorId, " +
                        "FROM TOTAL_FILM_DIRECTOR AS tf " +
                        "LEFT JOIN DIRECTORS AS d ON tf.director_id = d.id " +
                        "ORDER BY d.id;"
        );
        while (rows.next()) {
            Long totalDirectorFilmId = rows.getLong("ID");
            if (!result.containsKey(totalDirectorFilmId)) {
                TotalDirectorFilm totalDirectorFilm = buildModel(rows);
                result.put(totalDirectorFilmId, totalDirectorFilm);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<TotalDirectorFilm> findAllTotalDirectorFilm(Long filmId) {
        List<TotalDirectorFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_DIRECTOR WHERE film_id = ?;",
                filmId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<TotalDirectorFilm> findById(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_DIRECTOR WHERE director_id = ?;",
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else {
            return Optional.empty();
        }
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
    public void delete() {

    }

    @Override
    public void deleteAllByFilmId(Long filmId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_FILM_DIRECTOR WHERE film_id = ?;",
                filmId
        );
    }

    @Override
    public List<Film> findPopularFilmsByDirector(Long directorId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(generateSqlRequest() + "WHERE f.id IN ( SELECT film_id FROM " +
                "TOTAL_FILM_DIRECTOR WHERE director_id = ?) " +
                "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC;", directorId);
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = filmDao.buildModel(rows);
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
    public List<Film> findFilmsByDirectorSortedByYear(Long directorId) { // метод падает в постмане,
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
                    "ORDER BY EXTRACT(YEAR FROM f.release_date) ASC;", directorId);
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = filmDao.buildModel(rows);
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
    public void deleteAllByDirectorId(Long directorId) {

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

    protected TotalDirectorFilm buildModel(@NotNull SqlRowSet row) {
        return TotalDirectorFilm.builder()
                .filmId(row.getLong("FILM_ID"))
                .directorId(row.getLong("DIRECTOR_ID"))
                .build();
    }
}
