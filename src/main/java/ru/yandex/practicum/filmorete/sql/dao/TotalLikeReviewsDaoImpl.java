package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.TotalReviewLike;

import java.util.List;
import java.util.Optional;

public interface TotalLikeReviewsDaoImpl {
    List<TotalReviewLike> findAll();

    Optional<TotalReviewLike> findById(Long rowId);

    void insert(TotalReviewLike totalReviewLike);

    void update(TotalReviewLike totalReviewLike);

    void deleteById(Long rowId);
}
