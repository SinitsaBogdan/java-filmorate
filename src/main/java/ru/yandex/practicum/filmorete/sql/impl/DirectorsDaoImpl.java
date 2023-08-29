package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundGenreStorage;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.DirectorsDao;

import java.util.*;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceGenre.SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS;


@Slf4j
@Component
@RequiredArgsConstructor
public class DirectorsDaoImpl implements DirectorsDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Director> findAll() {
        List<Director> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM DIRECTORS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<Director> findById(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM DIRECTORS WHERE id = ?;",
                rowId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Director insert(Director director) {
        jdbcTemplate.update(
                "INSERT INTO DIRECTORS (name) " +
                        "VALUES (?);",
                director.getName()
        );
        Optional<Director> optional = findByName(director.getName());
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS); //создать новое исключение
        }
    }

    @Override
    public Director update(Director director) {
        jdbcTemplate.update(
                "UPDATE DIRECTORS SET name = ? WHERE id = ?;",
                director.getName(), director.getId()
        );
        return director;
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM DIRECTORS WHERE id = ?;",
                rowId
        );
    }

    protected Optional<Director> findByName(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM DIRECTORS WHERE name = ?;",
                name
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    protected Director buildModel(@NotNull SqlRowSet row) {
        return Director.builder()
                .id(row.getLong("ID"))
                .name(row.getString("NAME"))
                .build();
    }
}
