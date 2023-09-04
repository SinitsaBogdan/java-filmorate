package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Genre;
import java.util.List;
import java.util.Optional;

public interface RosterGenreDao {

    List<Genre> findAll();

    Optional<Genre> findAllByRowId(Integer id);

    Optional<Genre> findAllByName(String name);

    List<String> findAllColumnName();

    void insert(String name);

    void insert(Integer id, String name);

    void update(Integer searchId, String name);

    void deleteAll();

    void deleteAllById(Integer id);

    void deleteAllByName(String name);
}
