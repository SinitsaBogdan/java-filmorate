package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.TotalLikeReview;

import java.util.List;
import java.util.Optional;

public interface TotalLikeReviewDao {

    List<TotalLikeReview> findAll();

    List<TotalLikeReview> findAllByReviewId(Long reviewId);

    List<TotalLikeReview> findAllByUserId(Long userId);

    List<TotalLikeReview> findAllByIsPositive(Boolean isPositive);

    void insert(Long reviewId, Long userId, Boolean type);

    void update(Long reviewId, Long userId, Boolean type);

    void delete();

    void delete(Long reviewId, Long userId);

    void deleteAllTypeLike(Boolean type);

    void deleteAllReviewId(Long reviewId);

    void deleteAllUserId(Long userId);
}
