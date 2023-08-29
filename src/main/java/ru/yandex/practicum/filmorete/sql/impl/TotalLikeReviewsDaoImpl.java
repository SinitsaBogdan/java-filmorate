package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.TotalReviewLike;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalLikeReviewsDaoImpl implements ru.yandex.practicum.filmorete.sql.dao.TotalLikeReviewsDaoImpl {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TotalReviewLike> findAll() {

        Map<Long, TotalReviewLike> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "tl.review_id AS reviewId, " +
                        "tl.user_id  AS userId, " +
                        "tl.isPositive AS typeLike, " +
                        "FROM TOTAL_LIKE_REVIEWS AS tl " +
                        "LEFT JOIN REVIEWS AS r ON tl.review_id = r.id " +
                        "ORDER BY tl.review_id;"
        );
        while (rows.next()) {
            Long totalReviewLikeId = rows.getLong("REVIEW_ID");
            if (!result.containsKey(totalReviewLikeId)) {
                TotalReviewLike totalReviewLike = buildModel(rows);
                result.put(totalReviewLikeId, totalReviewLike);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<TotalReviewLike> findById(Long rowId) {

        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_LIKE_REVIEWS WHERE review_id = ?;",
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void insert(TotalReviewLike totalReviewLike) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_LIKE_REVIEWS (review_id, user_id, isPositive) " +
                        "VALUES (?, ?, ?);",
                totalReviewLike.getReviewId(), totalReviewLike.getUserId(), totalReviewLike.isTypeLike()
        );
    }

    @Override
    public void update(TotalReviewLike totalReviewLike) {
        jdbcTemplate.update(
                "UPDATE TOTAL_LIKE_REVIEWS SET review_id = ?  WHERE user_id = ?;",
                totalReviewLike.getReviewId(), totalReviewLike.getUserId()
        );
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_LIKE_REVIEWS WHERE review_id = ?;",
                rowId
        );
    }

    protected TotalReviewLike buildModel(@NotNull SqlRowSet row) {
        return TotalReviewLike.builder()
                .reviewId(row.getLong("REVIEW_ID"))
                .userId(row.getLong("USER_ID"))
                .typeLike(row.getBoolean("ISPOSITIVE"))
                .build();
    }
}
