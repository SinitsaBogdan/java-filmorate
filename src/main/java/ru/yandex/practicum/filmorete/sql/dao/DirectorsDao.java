package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorsDao {

    List<Director> findAll();

    Optional<Director> findById(Long rowId);

    void insert(Director director);

    void update(Director director);

    void deleteById(Long rowId);

}
