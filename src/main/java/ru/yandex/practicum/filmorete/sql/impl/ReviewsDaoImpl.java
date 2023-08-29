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

        Map<Long, Review> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "r.id AS id, " +
                        "r.content AS content, " +
                        "r.user_id AS userId, " +
                        "r.film_id AS filmId, " +
                        "r.type_id AS typeId, " +
                        "r.evaluation_id AS evalutionId, " +
                        "r.useful AS useful, " +
                        "FROM REVIEWS AS r " +
                        "ORDER BY r.id;"
        );
        while (rows.next()) {
            Long reviewsId = rows.getLong("ID");
            if (!result.containsKey(reviewsId)) {
                Review review = buildModel(rows);
                result.put(reviewsId, review);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<Review> findById(Long rowId) {
        Map<Long, Review> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "r.id AS id, " +
                        "r.content AS content, " +
                        "r.user_id AS userId, " +
                        "r.film_id AS filmId, " +
                        "r.type_id AS typeId, " +
                        "r.evaluation_id AS evalutionId, " +
                        "r.useful AS useful, " +
                        "FROM REVIEWS AS r " +
                        "ORDER BY r.id;",
                rowId
        );
        while (rows.next()) {
            Long reviewsId = rows.getLong("ID");
            if (!result.containsKey(reviewsId)) {
                Review review = buildModel(rows);
                result.put(rowId, review);
            }
        }
        return Optional.ofNullable(result.get(rowId));
    }

    @Override
    public void insert(Review reviews) {
        jdbcTemplate.update(
                "INSERT INTO REVIEWS (id, content, user_id, film_id, type_id, evaluation_id, useful) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);",
                reviews.getId(), reviews.getContent(), reviews.getUserId(), reviews.getFilmId(), reviews.getTypeId(),
                reviews.getEvaluationId(), reviews.getUseful()
        );
    }

    @Override
    public void update(Review reviews) {
        jdbcTemplate.update(
                "SELECT " +
                        "r.id AS id, " +
                        "r.content AS content, " +
                        "r.user_id AS userId, " +
                        "r.film_id AS filmId, " +
                        "r.type_id AS typeId, " +
                        "r.evaluation_id AS evalutionId, " +
                        "r.useful AS useful, " +
                        "FROM REVIEWS AS r " +
                        "ORDER BY r.id;",
                reviews.getId(), reviews.getContent(), reviews.getUserId(), reviews.getFilmId(), reviews.getTypeId(),
                reviews.getEvaluationId(), reviews.getUseful()
        );
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM REVIEWS WHERE id = ?;",
                rowId
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
