package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
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
                        "review_id, " +
                        "user_id, " +
                        "isPositive " +
                    "FROM TOTAL_LIKE_REVIEWS;"
        );
        while (rows.next()) result.add(FactoryModel.buildTotalLikeReview(rows));
        return result;
    }

    @Override
    public void insert(Long reviewId, Long userId, Boolean type) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_LIKE_REVIEWS (review_id, user_id, is_positive) " +
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
}
