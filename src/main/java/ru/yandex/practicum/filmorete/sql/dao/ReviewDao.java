package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> findAll();

    List<Review> findAll(Long userId);

    List<Review> findAll(Boolean isPositive);

    List<Review> findAll(Long filmId, Integer count);

    List<Review> findByUseful(Integer useful);

    Optional<Review> findById(Long reviewId);

    void insert(Long id, String content, Boolean isPositive, Long userId, Long filmId);

    Long insert(String content, Boolean isPositive, Long userId, Long filmId);

    void update(Long id, String content, Boolean isPositive);

    void updateUseful(Long id);

    void delete();

    void delete(Long reviewId);

    void deleteAllIsPositive(Boolean isPositive);

    void deleteAllUserId(Long userId);

    void deleteAllFilmId(Long filmId);

    void deleteAllTypeId(Integer typeId);

    void deleteAllEvaluationId(Integer evaluationId);

    void deleteAllUseful(Integer useful);
}
