package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Genre;
import java.util.List;
import java.util.Optional;

public interface RosterGenreDao {

    List<Genre> findAll();

    Optional<Genre> findAllByRowId(Integer rowId);

    Optional<Genre> findAllByName(String name);

    List<String> findAllColumnName();

    void insert(String name);

    void insert(Integer rowId, String name);

    void update(Integer searchRowId, String name);

    void deleteAll();

    void deleteAllByRowId(Integer rowId);

    void deleteAllByName(String name);
}
