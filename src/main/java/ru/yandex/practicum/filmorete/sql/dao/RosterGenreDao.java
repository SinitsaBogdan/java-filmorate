package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Genre;
import java.util.List;
import java.util.Optional;

public interface RosterGenreDao {

    List<Genre> findAll();

    Optional<Genre> findAll(Integer rowId);

    Optional<Genre> findAll(String name);

    List<String> findAllColumnName();

    void insert(String name);

    void insert(Integer rowId, String name);

    void update(Integer searchRowId, String name);

    void deleteAll();

    void deleteAll(Integer rowId);

    void deleteAll(String name);
}
