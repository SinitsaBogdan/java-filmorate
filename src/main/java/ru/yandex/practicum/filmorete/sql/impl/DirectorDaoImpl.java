package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
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
        List<Director> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM DIRECTORS;"
        );
        while (row.next()) result.add(FactoryModel.buildDirector(row));
        return result;
    }

    @Override
    public Optional<Director> findById(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM DIRECTORS " +
                    "WHERE id = ?;",
                rowId
        );
        if (row.next()) return Optional.of(FactoryModel.buildDirector(row));
        else return Optional.empty();
    }

    @Override
    public Long insert(String name) {
        jdbcTemplate.update(
                "INSERT INTO DIRECTORS (name) " +
                    "VALUES(?);",
                name
        );
        return jdbcTemplate.queryForObject(
                "SELECT MAX(id) FROM DIRECTORS;", Long.class
        );
    }

    @Override
    public void insert(Long rowId, String name) {
        jdbcTemplate.update(
                "INSERT INTO DIRECTORS (id, name) " +
                        "VALUES(?, ?);",
                rowId, name
        );
    }

    @Override
    public void update(Long id, String name) {
        jdbcTemplate.update(
                "UPDATE DIRECTORS " +
                    "SET name = ? " +
                    "WHERE id = ?;",
                name, id
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
}