package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum ReviewsRequests {
    SELECT_ALL__REVIEWS(
        "SELECT * FROM REVIEWS;"
    ),

    SELECT_ALL__REVIEWS__USER_ID(
        "SELECT * FROM REVIEWS " +
            "WHERE user_id = ?;"
    ),

    SELECT_ALL__REVIEWS__COUNT(
        "SELECT * " +
            "FROM REVIEWS " +
            "ORDER BY useful DESC " +
            "LIMIT ?;"
    ),

    SELECT_ALL__REVIEWS__FILM_ID_COUNT(
        "SELECT * " +
            "FROM REVIEWS " +
            "WHERE film_id = ? " +
            "ORDER BY useful DESC " +
            "LIMIT ?;"
    ),

    SELECT_ALL__REVIEWS__IS_POSITIVE(
        "SELECT * FROM REVIEWS " +
            "WHERE is_positive = ?;"
    ),

    SELECT_ALL__REVIEWS__USEFUL(
        "SELECT * FROM REVIEWS " +
            "WHERE useful = ?;"
    ),

    SELECT_ALL__REVIEWS__ID(
        "SELECT * FROM REVIEWS " +
            "WHERE id = ?;"
    ),

    SELECT_MAX_ID__REVIEWS__ID(
        "SELECT MAX(id) FROM REVIEWS"
    ),

    INSERT_ONE__REVIEWS_FULL__CONTENT_IS_POSITIVE_USER_FILM(
            "INSERT INTO REVIEWS (content, is_positive, user_id, film_id) " +
                "VALUES (?, ?, ?, ?);"
    ),

    INSERT_ONE__REVIEWS_FULL(
            "INSERT INTO REVIEWS (id, content, is_positive, user_id, film_id) " +
                "VALUES (?, ?, ?, ?, ?);"
    ),

    UPDATE_ONE__REVIEWS_SET_CONTENT_IS_POSITIVE__ID(
        "UPDATE REVIEWS " +
            "SET " +
                "content = ?, " +
                "is_positive = ? " +
            "WHERE id = ?;"
    ),

    UPDATE_ONE__REVIEWS_SET_USEFUL__ID(
        "UPDATE REVIEWS SET useful = (" +
            "(SELECT COUNT(*) FROM TOTAL_LIKE_REVIEWS WHERE review_id = ? AND is_positive = TRUE)" +
            " - " +
            "(SELECT COUNT(*) FROM TOTAL_LIKE_REVIEWS WHERE review_id = ? AND is_positive = FALSE)" +
            ") WHERE id = ?;"
    ),

    DELETE_ALL__REVIEWS(
        "DELETE FROM REVIEWS;"
    ),

    DELETE_ONE__REVIEWS__ID(
        "DELETE FROM REVIEWS " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__REVIEWS__USER_ID(
        "DELETE FROM REVIEWS " +
            "WHERE user_id = ?;"
    ),

    DELETE_ONE__REVIEWS__FILM_ID(
        "DELETE FROM REVIEWS " +
            "WHERE film_id = ?;"
    );

    private final String sql;

    ReviewsRequests(String sql) {
        this.sql = sql;
    }
}
