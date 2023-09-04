package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> findAll();

    List<Review> findAll(Long userId);

    List<Review> findAll(Boolean isPositive);

    List<Review> findAllIsFilmIdAndCount(Long filmId, Integer count);

    List<Review> findAllIsCount(Integer count);

    List<Review> findAllIsUseful(Integer useful);

    Optional<Review> findByReviewId(Long reviewId);

    Long insert(String content, Boolean isPositive, Long userId, Long filmId);

    void insert(Long id, String content, Boolean isPositive, Long userId, Long filmId);

    void update(Long id, String content, Boolean isPositive);

    void deleteAll();

    void deleteAllIsReviewId(Long reviewId);

    void deleteAllIsUserId(Long userId);

    void deleteAllIsFilmId(Long filmId);

    void recalculationPositive(Long id);
}