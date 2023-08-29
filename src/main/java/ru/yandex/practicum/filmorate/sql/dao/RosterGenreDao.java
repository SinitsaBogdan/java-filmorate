package ru.yandex.practicum.filmorate.sql.dao;

import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;
import java.util.Optional;

public interface RosterGenreDao {

    List<String> findAllName();

    List<Genre> findAllGenre();

    Optional<Genre> findGenre(Integer rowId);

    Optional<Genre> findGenre(String name);

    void insert(String name);

    void insert(Integer rowId, String name);

    void update(Integer searchRowId, String name);

    void delete();

    void delete(Integer rowId);

    void delete(String name);
}
