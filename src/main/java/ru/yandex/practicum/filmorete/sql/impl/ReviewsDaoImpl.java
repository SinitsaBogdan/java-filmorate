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

import static ru.yandex.practicum.filmorete.sql.requests.ReviewsRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewsDaoImpl implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> findAll() {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__REVIEWS.getSql());
        while (rows.next()) result.add(FactoryModel.buildReview(rows));
        return result;
    }

    @Override
    public List<Review> findAll(Long userId) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__REVIEWS__USER_ID.getSql(), userId
        );
        while (rows.next()) result.add(FactoryModel.buildReview(rows));
        return result;
    }

    @Override
    public List<Review> findAllIsCount(Integer count) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__REVIEWS__COUNT.getSql(), count
        );
        while (rows.next()) result.add(FactoryModel.buildReview(rows));
        return result;
    }

    @Override
    public List<Review> findAll(Long filmId, Integer count) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__REVIEWS__FILM_ID_COUNT.getSql(), filmId, count
        );
        while (rows.next()) result.add(FactoryModel.buildReview(rows));
        return result;
    }

    @Override
    public List<Review> findAll(Boolean isPositive) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__REVIEWS__IS_POSITIVE.getSql(), isPositive
        );
        while (rows.next()) result.add(FactoryModel.buildReview(rows));
        return result;
    }

    @Override
    public List<Review> findAllIsUseful(Integer useful) {
        List<Review> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__REVIEWS__USEFUL.getSql(), useful
        );
        while (rows.next()) result.add(FactoryModel.buildReview(rows));
        return result;
    }

    @Override
    public Optional<Review> findByReviewId(Long reviewId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__REVIEWS__ID.getSql(), reviewId
        );
        if (rows.next()) return Optional.of(FactoryModel.buildReview(rows));
        return Optional.empty();
    }

    @Override
    public void insert(Long id, String content, Boolean isPositive, Long userId, Long filmId) {
        jdbcTemplate.update(
                INSERT_ONE__REVIEWS_FULL.getSql(),
                id, content, isPositive, userId, filmId
        );
    }

    @Override
    public Long insert(String content, Boolean isPositive, Long userId, Long filmId) {
        jdbcTemplate.update(
                INSERT_ONE__REVIEWS_FULL__CONTENT_IS_POSITIVE_USER_FILM.getSql(),
                content, isPositive, userId, filmId
        );
        return jdbcTemplate.queryForObject(
                SELECT_MAX_ID__REVIEWS__ID.getSql(), Long.class
        );
    }

    @Override
    public void update(Long id, String content, Boolean isPositive) {
        jdbcTemplate.update(
                UPDATE_ONE__REVIEWS_SET_CONTENT_IS_POSITIVE__ID.getSql(),
                content, isPositive, id
        );
    }

    @Override
    public void recalculationPositive(Long id) {
        jdbcTemplate.update(UPDATE_ONE__REVIEWS_SET_USEFUL__ID.getSql(), id, id, id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL__REVIEWS.getSql());
    }

    @Override
    public void deleteAllIsReviewId(Long rowId) {
        jdbcTemplate.update(DELETE_ONE__REVIEWS__ID.getSql(), rowId);
    }

    @Override
    public void deleteAllIsUserId(Long userId) {
        jdbcTemplate.update(DELETE_ONE__REVIEWS__USER_ID.getSql(), userId);
    }

    @Override
    public void deleteAllIsFilmId(Long filmId) {
        jdbcTemplate.update(DELETE_ONE__REVIEWS__FILM_ID.getSql(), filmId);
    }
}
