package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum TotalLikeReviewRequests {
    SELECT_ALL_TOTAL_LIKE_REVIEW(
        "SELECT " +
                "review_id, " +
                "user_id, " +
                "isPositive " +
            "FROM TOTAL_LIKE_REVIEWS;"
    ),

    INSERT_ONE__TOTAL_LIKE_REVIEW__REVIEW_USER_IS_POSITIVE(
        "INSERT INTO TOTAL_LIKE_REVIEWS (review_id, user_id, is_positive) " +
            "VALUES (?, ?, ?);"
    ),

    UPDATE_ONE__TOTAL_LIKE_REVIEW__SET_REVIEW_IS_POSITIVE__USER(
        "UPDATE TOTAL_LIKE_REVIEWS SET review_id = ?, is_positive = ? " +
            "WHERE user_id = ?;"
    ),

    DELETE_ALL__TOTAL_LIKE_REVIEW(
        "DELETE FROM TOTAL_LIKE_REVIEWS;"
    ),

    DELETE_ONE__TOTAL_LIKE_REVIEW__REVIEW_USER(
        "DELETE FROM TOTAL_LIKE_REVIEWS " +
            "WHERE review_id = ? AND user_id = ?;"
    );

    private final String sql;

    TotalLikeReviewRequests(String sql) {
        this.sql = sql;
    }
}
