package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> findAll();

    Optional<Review> findById(Long rowId);

    void insert(Long id, String content, Boolean isPositive, Long userId, Long filmId);

    void insert(String content, Boolean isPositive, Long userId, Long filmId);

    void update(Long id, String content, Boolean isPositive, Long userId, Long filmId);

    void delete();

    void delete(Long rowId);

    void deleteAllIsPositive(Boolean isPositive);

    void deleteAllUserId(Long userId);

    void deleteAllFilmId(Long filmId);

    void deleteAllTypeId(Integer typeId);

    void deleteAllEvaluationId(Integer evaluationId);

    void deleteAllUseful(Integer useful);
}