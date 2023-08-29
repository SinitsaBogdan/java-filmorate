package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.RosterMpaDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RosterMpaDaoImpl implements RosterMpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT name FROM ROSTER_MPA;"
        );
        while (rows.next()) result.add(rows.getString("name"));
        return result;
    }

    @Override
    public List<String> findAllDescription() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT description " +
                    "FROM ROSTER_MPA;"
        );
        while (rows.next()) result.add(rows.getString("description"));
        return result;
    }

    @Override
    public List<Mpa> findAllMpa() {
        List<Mpa> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_MPA;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<Mpa> findMpa(Integer rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_MPA " +
                    "WHERE id = ?;",
                rowId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Optional<Mpa> findMpa(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_MPA " +
                    "WHERE name = ?;",
                name
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name, String description) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_MPA (name, description) " +
                    "VALUES(?, ?);",
                name, description
        );
    }

    @Override
    public void insert(Integer rowId, String name, String description) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_MPA (id, name, description) " +
                    "VALUES(?, ?, ?);",
                rowId, name, description
        );
    }

    @Override
    public void update(Integer searchRowId, String name, String description) {
        jdbcTemplate.update(
                "UPDATE ROSTER_MPA " +
                    "SET " +
                        "name = ?, " +
                        "description = ? " +
                    "WHERE id = ?;",
                name, description, searchRowId
        );
    }

    @Override
    public void update(String searchName, String name, String description) {
        jdbcTemplate.update(
                "UPDATE ROSTER_MPA " +
                    "SET " +
                        "name = ?, " +
                        "description = ? " +
                    "WHERE name = ?;",
                name, description, searchName
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_MPA;"
        );
    }

    @Override
    public void delete(Integer rowId) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_MPA " +
                    "WHERE id = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_MPA " +
                    "WHERE name = ?;",
                name
        );
    }

    protected Mpa buildModel(@NotNull SqlRowSet row) {
        return Mpa.builder()
                .id(row.getInt("id"))
                .name(row.getString("name"))
                .description(row.getString("description"))
                .build();
    }
}