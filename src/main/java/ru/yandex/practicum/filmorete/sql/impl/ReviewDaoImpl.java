package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.sql.dao.ReviewDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewDaoImpl implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> findAll() {
        List<Review> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS;"
        );
        while (row.next()) result.add(FactoryModel.buildReview(row));
        return result;
    }

    @Override
    public List<Review> findAll(Long userId) {
        List<Review> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE user_id = ?;", userId
        );
        while (row.next()) result.add(FactoryModel.buildReview(row));
        return result;
    }

    @Override
    public List<Review> findAllIsCount(Integer count) {
        List<Review> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS ORDER BY useful DESC LIMIT ?;",
                count
        );
        while (row.next()) result.add(FactoryModel.buildReview(row));
        return result;
    }

    @Override
    public List<Review> findAllFilmIdAndIsCount(Long filmId, Integer count) {
        List<Review> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE film_id = ? ORDER BY useful DESC LIMIT ?;",
                filmId, count
        );
        while (row.next()) result.add(FactoryModel.buildReview(row));
        return result;
    }



    @Override
    public List<Review> findAll(Boolean isPositive) {
        List<Review> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE is_positive = ?;", isPositive
        );
        while (row.next()) result.add(FactoryModel.buildReview(row));
        return result;
    }

    @Override
    public List<Review> findAll(Integer useful) {
        List<Review> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS WHERE useful = ?;", useful
        );
        while (row.next()) result.add(FactoryModel.buildReview(row));
        return result;
    }

    @Override
    public Optional<Review> findByReviewId(Long reviewId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS " +
                    "WHERE id = ?;",
                reviewId
        );
        if (row.next()) return Optional.of(FactoryModel.buildReview(row));
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
}
