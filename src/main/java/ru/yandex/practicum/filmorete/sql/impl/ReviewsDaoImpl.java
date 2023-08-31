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
    public Optional<Review> findById(Long rowId) {
        Map<Long, Review> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM REVIEWS " +
                    "WHERE id = ?;",
                rowId
        );
        while (rows.next()) {
            Long reviewsId = rows.getLong("ID");
            if (!result.containsKey(reviewsId)) result.put(rowId, buildModel(rows));
        }
        return Optional.ofNullable(result.get(rowId));
    }

    @Override
    public void insert(Long id, String content, Boolean is_positive, Long userId, Long filmId) {
        jdbcTemplate.update(
                "INSERT INTO REVIEWS (id, content, is_positive, user_id, film_id) " +
                    "VALUES (?, ?, ?, ?, ?);",
                id, content, is_positive, userId, filmId
        );
    }

    @Override
    public void insert(String content, Boolean is_positive, Long userId, Long filmId) {
        jdbcTemplate.update(
                "INSERT INTO REVIEWS (content, is_positive, user_id, film_id) " +
                        "VALUES (?, ?, ?, ?);",
                content, is_positive, userId, filmId
        );
    }

    @Override
    public void update(Long id, String content, Boolean is_positive, Long userId, Long filmId) {
        jdbcTemplate.update(
                "UPDATE REVIEWS " +
                    "SET " +
                        "content = ?, " +
                        "status = ?, " +
                        "userId = ?, " +
                        "filmId = ? " +
                    "WHERE user_id = ?;",
                content, is_positive, userId, filmId, id
        );
    }

    @Override
    public void delete() {
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

    @Override
    public void deleteAllTypeId(Integer typeId) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS " +
                        "WHERE type_id = ?;",
                typeId
        );
    }

    @Override
    public void deleteAllEvaluationId(Integer evaluationId) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS " +
                        "WHERE evaluation_id = ?;",
                evaluationId
        );
    }

    @Override
    public void deleteAllUseful(Integer useful) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS " +
                        "WHERE useful = ?;",
                useful
        );
    }

    protected Review buildModel(@NotNull SqlRowSet row) {
        return Review.builder()
                .id(row.getLong("ID"))
                .content(row.getString("CONTENT"))
                .userId(row.getLong("USER_ID"))
                .filmId(row.getLong("FILM_ID"))
                .typeId(row.getLong("TYPE_ID"))
                .evaluationId(row.getLong("EVALUATION_ID"))
                .useful(row.getInt("USEFUL"))
                .build();
    }
}
