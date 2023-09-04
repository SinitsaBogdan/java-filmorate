package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorDao {

    List<Director> find();

    Optional<Director> findById(Long rowId);

    Long insertByName(String name);

    void insert(Long rowId, String name);

    void update(Long id, String name);

    void delete();

    void deleteById(Long rowId);

    void deleteByName(String name);
}
