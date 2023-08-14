package ru.yandex.practicum.filmorete.sql.impl;

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
@Qualifier("TotalFilmLikeDaoImpl")
public class TotalFilmLikeDaoImpl implements TotalFilmLikeDao {

    private final JdbcTemplate jdbcTemplate;

    private final TotalGenreFilmDao totalGenreFilmDao;

    private final FilmDaoImpl filmDao;

    private final UserDaoImpl userDao;

    private TotalFilmLikeDaoImpl(JdbcTemplate jdbcTemplate, TotalGenreFilmDao totalGenreFilmDao, FilmDaoImpl filmDao, UserDaoImpl userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalGenreFilmDao = totalGenreFilmDao;
        this.filmDao = filmDao;
        this.userDao = userDao;
    }

    @Override
    public List<Film> findPopularFilms(Integer limit) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "F.ID AS FILM_ID, " +
                        "F.NAME AS FILM_NAME, " +
                        "F.DESCRIPTION AS FILM_DESCRIPTION, " +
                        "F.RELEASE_DATE AS FILM_RELEASE_DATE, " +
                        "F.DURATION AS FILM_DURATION, " +
                        "R.ID AS MPA_ID, " +
                        "R.NAME AS MPA_NAME, " +
                        "G.ID AS GENRE_ID, " +
                        "G.NAME AS GENRE_NAME, " +
                        "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS L WHERE L.FILM_ID = F.ID ) AS SIZE_LIKE " +
                    "FROM FILMS AS F " +
                    "INNER JOIN ROSTER_MPA AS R ON F.MPA_ID = R.ID " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS T ON F.ID=T.FILM_ID " +
                    "LEFT JOIN ROSTER_GENRE AS G ON T.GENRE_ID=G.ID " +
                    "WHERE F.ID IN ( " +
                        "SELECT F.ID FROM FILMS AS F " +
                        "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS L WHERE L.FILM_ID = F.ID ) DESC " +
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
                    "WHERE ID IN (" +
                        "SELECT USER_ID FROM TOTAL_FILM_LIKE " +
                        "WHERE FILM_ID = ?" +
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
                        "F.ID AS FILM_ID, " +
                        "F.NAME AS FILM_NAME, " +
                        "F.DESCRIPTION AS FILM_DESCRIPTION, " +
                        "F.RELEASE_DATE AS FILM_RELEASE_DATE, " +
                        "F.DURATION AS FILM_DURATION, " +
                        "R.ID AS MPA_ID, " +
                        "R.NAME AS MPA_NAME, " +
                        "G.ID AS GENRE_ID, " +
                        "G.NAME AS GENRE_NAME, " +
                        "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS L WHERE L.FILM_ID = F.ID ) AS SIZE_LIKE " +
                    "FROM FILMS AS F " +
                    "INNER JOIN ROSTER_MPA AS R ON F.MPA_ID = R.ID " +
                    "LEFT JOIN TOTAL_GENRE_FILM AS T ON F.ID=T.FILM_ID " +
                    "LEFT JOIN ROSTER_GENRE AS G ON T.GENRE_ID=G.ID " +
                    "WHERE F.ID IN (SELECT FILM_ID FROM TOTAL_FILM_LIKE WHERE USER_ID = ?);",
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
                "SELECT * FROM TOTAL_FILM_LIKE WHERE FILM_ID = ?;",
                filmId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalFilmLike> findAllTotalFilmLikeByUserId(Long userId) {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE USER_ID = ?;",
                userId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public void insert(Long filmId, Long userId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_FILM_LIKE (FILM_ID, USER_ID) VALUES(?, ?);",
                filmId, userId
        );
    }

    @Override
    public void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId) {
        jdbcTemplate.update(
                "UPDATE TOTAL_FILM_LIKE SET FILM_ID = ?, USER_ID = ? WHERE FILM_ID = ? AND USER_ID = ?;",
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
                "DELETE FROM TOTAL_FILM_LIKE WHERE FILM_ID = ? AND USER_ID = ?;",
                filmId, userId
        );
    }

    protected TotalFilmLike buildModel(@NotNull SqlRowSet row) {
        return TotalFilmLike.builder()
                .filmId(row.getLong("FILM_ID"))
                .userId(row.getLong("USER_ID"))
                .build();
    }
}
