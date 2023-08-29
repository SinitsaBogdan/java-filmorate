package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Evaluation;

import java.util.List;
import java.util.Optional;

public interface RosterEvaluationDao {

    List<Evaluation> findAll();

    Optional<Evaluation> findById(Long rowId);

    void insert(Evaluation evaluation);

    void update(Evaluation evaluation);

    void delete();

    void delete(Long rowId);

    void delete(String name);
}
