package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Mpa;
import java.util.List;
import java.util.Optional;

public interface EnumMpaDao {

    Optional<Long> findLastId();

    Optional<List<String>> findAllName();

    Optional<List<String>> findAllDescription();

    Optional<List<Mpa>> findRows();

    Optional<Mpa> findRow(Integer rowId);

    Optional<Mpa> findRow(String name);

    void insert(String name, String description);

    void insert(Integer rowId, String name, String description);

    void update(Integer searchRowId, String name, String description);

    void update(String searchName, String name, String description);

    void delete();

    void delete(Integer rowId);

    void delete(String name);
}
