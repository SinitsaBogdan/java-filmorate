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

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableTotalFilmLike.*;

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
                SELECT_TABLE_TOTAL_FILM_LIKE_POPULAR_FILMS_AND_ORDER_BY_AND_LIMIT_ON_MODEL_FILM.getTemplate(),
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
                SELECT_ALL_USERS_TO_LIKE_FILM_ON_MODEL_USER.getTemplate(),
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
                SELECT_ALL_FILMS_TO_LIKE_FILM_ON_MODEL_FILMS.getTemplate(),
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
                SELECT_TABLE_TOTAL_FILM_LIKE__ALL_ROWS.getTemplate()
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
                SELECT_TABLE_TOTAL_FILM_LIKE__ROW_BY_FILM_ID.getTemplate(),
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
                SELECT_TABLE_TOTAL_FILM_LIKE__ROW_BY_USER_ID.getTemplate(),
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
                INSERT_TABLE_TOTAL_FILM_LIKE.getTemplate(),
                filmId, userId
        );
    }

    @Override
    public void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId) {
        jdbcTemplate.update(
                UPDATE_TABLE_TOTAL_FILM_LIKE__ROW_BY_FILM_ID_AND_USER_ID.getTemplate(),
                filmId, userId, searchFilmId, searchUserId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_FILM_LIKE__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Long filmId, Long userId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_FILM_LIKE__ROW_BY_FILM_ID_AND_USER_ID.getTemplate(),
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
