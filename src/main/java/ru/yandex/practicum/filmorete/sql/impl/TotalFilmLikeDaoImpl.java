package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.util.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class TotalFilmLikeDaoImpl implements TotalFilmLikeDao {

    private final JdbcTemplate jdbcTemplate;

    private final TotalGenreFilmDao totalGenreFilmDao;

    private final FilmDaoImpl filmDao;

    private final UserDaoImpl userDao;


    @Override
    public List<Film> findPopularFilms(Integer limit) {
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
                        "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) AS size_like " +
                        "FROM FILMS AS f " +
                        "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                        "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                        "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                        "WHERE f.id IN ( " +
                        "SELECT f.id FROM FILMS AS f " +
                        "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC " +
                        "LIMIT ? " +
                        ");",
                limit
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(filmId)) {
                Film film = filmDao.buildModel(rows);
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
    public List<User> findUserToLikeFilm(Long filmId) {
        List<User> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                        "FROM USERS " +
                        "WHERE id IN (" +
                        "SELECT user_id FROM TOTAL_FILM_LIKE " +
                        "WHERE film_id = ?" +
                        ");",
                filmId
        );
        while (rows.next()) result.add(userDao.buildModel(rows));
        return result;
    }

    @Override
    public List<Film> findFilmToLikeUser(Long userId) {
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
                        "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) AS size_like " +
                        "FROM FILMS AS f " +
                        "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                        "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                        "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                        "WHERE f.id IN (SELECT film_id FROM TOTAL_FILM_LIKE WHERE user_id = ?);",
                userId
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(filmId)) {
                Film film = filmDao.buildModel(rows);
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
    public List<TotalFilmLike> findAllTotalFilmLike() {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalFilmLike> findAllTotalFilmLikeByFilmId(Long filmId) {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE film_id = ?;",
                filmId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalFilmLike> findAllTotalFilmLikeByUserId(Long userId) {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE user_id = ?;",
                userId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public void insert(Long filmId, Long userId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_FILM_LIKE (film_id, user_id) VALUES(?, ?);",
                filmId, userId
        );
    }

    @Override
    public void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId) {
        jdbcTemplate.update(
                "UPDATE TOTAL_FILM_LIKE SET film_id = ?, user_id = ? WHERE film_id = ? AND user_id = ?;",
                filmId, userId, searchFilmId, searchUserId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_FILM_LIKE;"
        );
    }

    @Override
    public void delete(Long filmId, Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_FILM_LIKE WHERE film_id = ? AND user_id = ?;",
                filmId, userId
        );
    }

    protected TotalFilmLike buildModel(@NotNull SqlRowSet row) {
        return TotalFilmLike.builder()
                .filmId(row.getLong("film_id"))
                .userId(row.getLong("user_id"))
                .build();
    }
}