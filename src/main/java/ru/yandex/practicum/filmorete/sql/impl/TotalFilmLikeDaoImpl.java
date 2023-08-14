package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.util.*;


@Slf4j
@Component
@Primary
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
    public Optional<List<Film>> findPopularFilms(Integer limit) {
        List<Film> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ROSTER_MPA.ID AS MPA_ID, " +
                        "ROSTER_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION, " +
                        "(" +
                            "SELECT COUNT(*) " +
                            "FROM TOTAL_FILM_LIKE " +
                            "WHERE TOTAL_FILM_LIKE.FILM_ID = FILMS.ID" +
                        ") AS SIZE_LIKE " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ROSTER_MPA AS ROSTER_MPA ON FILMS.MPA_ID = ROSTER_MPA.ID " +
                    "ORDER BY SIZE_LIKE DESC " +
                    "LIMIT ?;",
                limit
        );
        while (rows.next()) {
            Optional<List<Genre>> optional = totalGenreFilmDao.findAllRowsSearchFilmIdByGenreId(rows.getLong("ID"));
            result.add(filmDao.buildModel(rows, optional.orElseGet(ArrayList::new)));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<User>> findUserToLikeFilm(Long filmId) {
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
        while (rows.next()) {
            result.add(userDao.buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<Film>> findFilmToLikeUser(Long userId) {
        List<Film> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ROSTER_MPA.ID AS MPA_ID, " +
                        "ROSTER_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION, " +
                        "(" +
                            "SELECT COUNT(*) " +
                            "FROM TOTAL_FILM_LIKE " +
                            "WHERE TOTAL_FILM_LIKE.FILM_ID = FILMS.ID" +
                        ") AS SIZE_LIKE " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ROSTER_MPA AS ROSTER_MPA ON FILMS.MPA_ID = ROSTER_MPA.ID " +
                    "WHERE FILMS.ID IN (" +
                        "SELECT FILM_ID FROM TOTAL_FILM_LIKE " +
                        "WHERE USER_ID = ?" +
                    ");",
                userId
        );
        while (rows.next()) {
            Optional<List<Genre>> optional = totalGenreFilmDao.findAllRowsSearchFilmIdByGenreId(rows.getLong("ID"));
            result.add(filmDao.buildModel(rows, optional.orElseGet(ArrayList::new)));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalFilmLike>> findRows() {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE;"
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalFilmLike>> findRowsByFilmId(Long filmId) {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE FILM_ID = ?;",
                filmId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalFilmLike>> findRowsByUserId(Long userId) {
        List<TotalFilmLike> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE USER_ID = ?;",
                userId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
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
