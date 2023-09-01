package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RosterGenreDaoImpl implements RosterGenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT name FROM ROSTER_GENRE;"
        );
        while (row.next()) result.add(row.getString("name"));
        return result;
    }

    @Override
    public List<Genre> findAllGenre() {
        List<Genre> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE " +
                    "ORDER BY id ASC;"
        );
        while (row.next()) result.add(FactoryModel.buildGenre(row));
        return result;
    }

    @Override
    public Optional<Genre> findGenre(Integer rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE " +
                    "WHERE id = ?;",
                rowId
        );
        if (row.next()) return Optional.of(FactoryModel.buildGenre(row));
        else return Optional.empty();
    }

    @Override
    public Optional<Genre> findGenre(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE " +
                    "WHERE name = ?;",
                name
        );
        if (row.next()) return Optional.of(FactoryModel.buildGenre(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_GENRE (name) " +
                    "VALUES(?);",
                name
        );
    }

    @Override
    public void insert(Integer rowId, String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_GENRE (id, name) " +
                    "VALUES(?, ?);",
                rowId, name
        );
    }

    @Override
    public void update(Integer searchRowId, String name) {
        jdbcTemplate.update(
                "UPDATE ROSTER_GENRE " +
                    "SET name = ? " +
                    "WHERE id = ?;",
                name, searchRowId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_GENRE;"
        );
    }

    @Override
    public void delete(Integer rowId) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_GENRE " +
                    "WHERE id = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_GENRE " +
                    "WHERE name = ?;",
                name
        );
    }
}