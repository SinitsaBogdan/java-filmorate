package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectorDaoImpl implements DirectorDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Director> findAll() {
        Map<Long, Director> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "d.id AS id, " +
                        "d.NAME AS name, " +
                        "FROM DIRECTORS AS d " +
                        "LEFT JOIN TOTAL_FILM_DIRECTOR AS tf ON d.id = tf.director_id " +
                        "ORDER BY d.id;"
        );
        while (rows.next()) {
            Long directorId = rows.getLong("ID");
            if (!result.containsKey(directorId)) {
                Director director = buildModel(rows);
                result.put(directorId, director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<Director> findById(Long rowId) {
        Map<Long, Director> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "d.id AS id, " +
                        "d.NAME AS name, " +
                        "FROM DIRECTORS AS d " +
                        "LEFT JOIN TOTAL_FILM_DIRECTOR AS tf ON d.id = tf.director_id " +
                        "ORDER BY d.id;",
                rowId
        );
        while (rows.next()) {
            Long directorId = rows.getLong("ID");
            if (!result.containsKey(directorId)) {
                Director director = buildModel(rows);
                result.put(rowId, director);
            }
        }
        return Optional.ofNullable(result.get(rowId));
    }

    @Override
    public void insert(Long id, String name) {
        jdbcTemplate.update(
                "INSERT INTO DIRECTORS (id, name) " +
                    "VALUES (?, ?);",
                id, name
        );

    }

    @Override
    public void update(Long id, String name) {
        jdbcTemplate.update(
                "UPDATE DIRECTORS " +
                    "SET " +
                        "id = ?, " +
                        "name = ? " +
                    "WHERE id = ?;",
                id, name
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM DIRECTORS;"
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM DIRECTORS WHERE id = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM DIRECTORS WHERE name = ?;",
                name
        );
    }

    protected Director buildModel(@NotNull SqlRowSet row) {
        return Director.builder()
                .id(row.getLong("ID"))
                .name(row.getString("NAME"))
                .build();
    }
}
