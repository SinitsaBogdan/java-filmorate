package ru.yandex.practicum.filmorete.sql.dao;


import ru.yandex.practicum.filmorete.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    List<Review> findAll();

    Optional<Review> findById(Long rowId);

    void insert(Review reviews);

    void update(Review reviews);

    void deleteById(Long rowId);
}
