package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorDao {

    List<Director> findAll();

    Optional<Director> findById(Long rowId);

    Optional<Director> findByName(String name);

    Long insert(String name);

    void insert(Long rowId, String name);

    void update(Long id, String name);

    void deleteAll();

    void deleteById(Long rowId);

    void deleteByName(String name);
}
