package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.TotalLikeReview;

import java.util.List;

public interface TotalLikeReviewDao {

    List<TotalLikeReview> findAll();

    void insert(Long reviewId, Long userId, Boolean type);

    void update(Long reviewId, Long userId, Boolean type);

    void delete();

    void delete(Long reviewId, Long userId);
}
