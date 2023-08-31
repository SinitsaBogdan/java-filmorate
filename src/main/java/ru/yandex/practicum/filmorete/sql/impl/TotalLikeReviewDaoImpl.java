package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.TotalLikeReview;
import ru.yandex.practicum.filmorete.sql.dao.TotalLikeReviewDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalLikeReviewDaoImpl implements TotalLikeReviewDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TotalLikeReview> findAll() {
        List<TotalLikeReview> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "t.review_id AS reviewId, " +
                        "t.user_id  AS userId, " +
                        "t.isPositive AS typeLike " +
                    "FROM TOTAL_LIKE_REVIEWS AS t;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalLikeReview> findAllByReviewId(Long reviewId) {
        List<TotalLikeReview> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_LIKE_REVIEWS " +
                    "WHERE review_id = ?;",
                reviewId
        );
        while (row.next()) result.add(buildModel(row));
        return result;
    }

    @Override
    public List<TotalLikeReview> findAllByUserId(Long userId) {
        List<TotalLikeReview> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_LIKE_REVIEWS " +
                        "WHERE user_id = ?;",
                userId
        );
        while (row.next()) result.add(buildModel(row));
        return result;
    }

    @Override
    public List<TotalLikeReview> findAllByIsPositive(Boolean isPositive) {
        List<TotalLikeReview> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_LIKE_REVIEWS " +
                        "WHERE is_positive = ?;",
                isPositive
        );
        while (row.next()) result.add(buildModel(row));
        return result;
    }

    @Override
    public void insert(Long reviewId, Long userId, Boolean type) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_LIKE_REVIEWS (review_id, user_id, isPositive) " +
                    "VALUES (?, ?, ?);",
                reviewId, userId, type
        );
    }

    @Override
    public void update(Long reviewId, Long userId, Boolean type) {
        jdbcTemplate.update(
                "UPDATE TOTAL_LIKE_REVIEWS SET review_id = ?, is_positive = ? " +
                    "WHERE user_id = ?;",
                reviewId, type, userId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_LIKE_REVIEWS;"
        );
    }

    @Override
    public void delete(Long reviewId, Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_LIKE_REVIEWS " +
                    "WHERE review_id = ? AND user_id = ?;",
                reviewId, userId
        );
    }

    @Override
    public void deleteAllReviewId(Long reviewId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_LIKE_REVIEWS " +
                    "WHERE review_id = ?;",
                reviewId
        );
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_LIKE_REVIEWS " +
                    "WHERE user_id = ?;",
                userId
        );
    }

    @Override
    public void deleteAllTypeLike(Boolean type) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_LIKE_REVIEWS " +
                "WHERE isPositive = ?;",
                type
        );
    }

    protected TotalLikeReview buildModel(@NotNull SqlRowSet row) {
        return TotalLikeReview.builder()
                .reviewId(row.getLong("REVIEW_ID"))
                .userId(row.getLong("USER_ID"))
                .typeLike(row.getBoolean("ISPOSITIVE"))
                .build();
    }
}
