package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.sql.dao.ReviewDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewsDaoImpl implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> findAll() {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<Review> findReviewIsUserId(Long rowId) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS " +
                        "WHERE user_id = ?;",
                rowId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<Review> findIsFilmId(Long rowId) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS " +
                        "WHERE film_id = ?;",
                rowId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<Review> findById(Long rowId) {
        return Optional.empty();
    }

    @Override
    public List<Review> findAll(Long userId) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE user_id = ?;", userId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<Review> findAllIsCount(Integer count) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS ORDER BY useful DESC LIMIT ?;",
                count
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<Review> findAllFilmIdAndIsCount(Long filmId, Integer count) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE film_id = ? ORDER BY useful DESC LIMIT ?;",
                filmId, count
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }



    @Override
    public List<Review> findAll(Boolean isPositive) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE is_positive = ?;", isPositive
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<Review> findAll(Integer useful) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE useful = ?;", useful
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<Review> findByReviewId(Long reviewId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS " +
                    "WHERE id = ?;",
                reviewId
        );
        if (rows.next()) return Optional.of(buildModel(rows));
        return Optional.empty();
    }

    @Override
    public void insert(Long id, String content, Boolean isPositive, Long userId, Long filmId) {
        jdbcTemplate.update(
                "INSERT INTO REVIEWS (id, content, is_positive, user_id, film_id) " +
                    "VALUES (?, ?, ?, ?, ?);",
                id, content, isPositive, userId, filmId
        );
    }

    @Override
    public Long insert(String content, Boolean isPositive, Long userId, Long filmId) {
        jdbcTemplate.update(
                "INSERT INTO REVIEWS (content, is_positive, user_id, film_id) " +
                    "VALUES (?, ?, ?, ?);",
                content, isPositive, userId, filmId
        );
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM REVIEWS", Long.class);
    }

    @Override
    public void update(Long id, String content, Boolean isPositive) {
        jdbcTemplate.update(
                "UPDATE REVIEWS " +
                    "SET " +
                        "content = ?, " +
                        "is_positive = ? " +
                    "WHERE id = ?;",
                content, isPositive, id
        );
    }

    @Override
    public void recalculationPositive(Long id) {
        jdbcTemplate.update(
                "UPDATE REVIEWS SET useful = (" +
                        "(SELECT COUNT(*) FROM TOTAL_LIKE_REVIEWS WHERE review_id = ? AND is_positive = TRUE)" +
                        " - " +
                        "(SELECT COUNT(*) FROM TOTAL_LIKE_REVIEWS WHERE review_id = ? AND is_positive = FALSE)" +
                    ") WHERE id = ?;", id, id, id
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS;"
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS WHERE id = ?;",
                rowId
        );
    }

    @Override
    public void deleteAllIsPositive(Boolean isPositive) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS " +
                        "WHERE isPositive = ?;",
                isPositive
        );
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS " +
                        "WHERE user_id = ?;",
                userId
        );
    }

    @Override
    public void deleteAllFilmId(Long filmId) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS " +
                        "WHERE film_id = ?;",
                filmId
        );
    }

    protected Review buildModel(@NotNull SqlRowSet row) {
        return Review.builder()
                .reviewId(row.getLong("ID"))
                .content(row.getString("CONTENT"))
                .isPositive(row.getBoolean("IS_POSITIVE"))
                .userId(row.getLong("USER_ID"))
                .filmId(row.getLong("FILM_ID"))
                .useful(row.getInt("USEFUL"))
                .build();
    }
}
