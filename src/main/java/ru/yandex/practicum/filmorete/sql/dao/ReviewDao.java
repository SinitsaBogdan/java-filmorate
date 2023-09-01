package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> findAll();

    List<Review> findAll(Long userId);

    List<Review> findAll(Boolean isPositive);

    List<Review> findAllIsCount(Integer count);

    List<Review> findAllFilmIdAndIsCount(Long filmId, Integer count);

    List<Review> findAll(Integer useful);

    Optional<Review> findByReviewId(Long reviewId);

    void insert(Long id, String content, Boolean isPositive, Long userId, Long filmId);

    Long insert(String content, Boolean isPositive, Long userId, Long filmId);

    void update(Long id, String content, Boolean isPositive);

    void recalculationPositive(Long id);

    void delete(Long reviewId);

    void deleteAll();

    void deleteAllUserId(Long userId);

    void deleteAllFilmId(Long filmId);
}