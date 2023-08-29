package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.TypeReview;

import java.util.List;
import java.util.Optional;

public interface RosterTypeReviewDao {

    List<TypeReview> findAll();

    Optional<TypeReview> findById(Long rowId);

    void insert(TypeReview typeReview);

    void update(TypeReview typeReview);

    void deleteById(Long rowId);
}
