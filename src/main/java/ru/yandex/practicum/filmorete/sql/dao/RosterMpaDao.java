package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Mpa;
import java.util.List;
import java.util.Optional;

public interface RosterMpaDao {

    List<String> findAllName();

    List<String> findAllDescription();

    List<Mpa> findAllMpa();

    Optional<Mpa> findMpaByRowId(Integer rowId);

    Optional<Mpa> findMpaByName(String name);

    void insert(String name, String description);

    void insert(Integer rowId, String name, String description);

    void update(Integer searchRowId, String name, String description);

    void update(String searchName, String name, String description);

    void delete();

    void deleteByRowId(Integer rowId);

    void deleteByName(String name);
}
