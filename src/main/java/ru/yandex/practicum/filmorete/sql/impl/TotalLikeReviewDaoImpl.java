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

import static ru.yandex.practicum.filmorete.sql.requests.TotalLikeReviewRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalLikeReviewDaoImpl implements TotalLikeReviewDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TotalLikeReview> findAll() {
        List<TotalLikeReview> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
            SELECT_ALL_TOTAL_LIKE_REVIEW.getSql()
        );
        while (row.next()) result.add(FactoryModel.buildTotalLikeReview(row));
        return result;
    }

    @Override
    public void insert(Long reviewId, Long userId, Boolean type) {
        jdbcTemplate.update(
            INSERT_ONE__TOTAL_LIKE_REVIEW__REVIEW_USER_IS_POSITIVE.getSql(),
                reviewId, userId, type
        );
    }

    @Override
    public void update(Long reviewId, Long userId, Boolean type) {
        jdbcTemplate.update(
            UPDATE_ONE__TOTAL_LIKE_REVIEW__SET_REVIEW_IS_POSITIVE__USER.getSql(),
                reviewId, type, userId
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
            DELETE_ALL__TOTAL_LIKE_REVIEW.getSql()
        );
    }

    @Override
    public void delete(Long reviewId, Long userId) {
        jdbcTemplate.update(
            DELETE_ONE__TOTAL_LIKE_REVIEW__REVIEW_USER.getSql(),
                reviewId, userId
        );
    }
}
