package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.RosterMpaDao;

import java.util.*;


@Slf4j
@Component
@Primary
@Qualifier("RosterMpaDaoImpl")
public class RosterMpaDaoImpl implements RosterMpaDao {

    private final JdbcTemplate jdbcTemplate;

    private RosterMpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT NAME FROM ROSTER_MPA;"
        );
        while (rows.next()) result.add(rows.getString("NAME"));
        return result;
    }

    @Override
    public List<String> findAllDescription() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT DESCRIPTION FROM ROSTER_MPA;"
        );
        while (rows.next()) result.add(rows.getString("DESCRIPTION"));
        return result;
    }

    @Override
    public List<Mpa> findAllMpa()  {
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
                "SELECT * FROM ROSTER_MPA WHERE ID = ?;",
                rowId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Optional<Mpa> findMpa(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_MPA WHERE NAME = ?;",
                name
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name, String description) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_MPA (NAME, DESCRIPTION) VALUES(?, ?);",
                name, description
        );
    }

    @Override
    public void insert(Integer rowId, String name, String description) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_MPA (ID, NAME, DESCRIPTION) VALUES(?, ?, ?);",
                rowId, name, description
        );
    }

    @Override
    public void update(Integer searchRowId, String name, String description) {
        jdbcTemplate.update(
                "UPDATE ROSTER_MPA SET NAME = ?, DESCRIPTION = ? WHERE ID = ?;",
                name, description, searchRowId
        );
    }

    @Override
    public void update(String searchName, String name, String description) {
        jdbcTemplate.update(
                "UPDATE ROSTER_MPA SET NAME = ?, DESCRIPTION = ? WHERE NAME = ?;",
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
                "DELETE FROM ROSTER_MPA WHERE ID = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_MPA WHERE NAME = ?;",
                name
        );
    }

    protected Mpa buildModel(@NotNull SqlRowSet row) {
        return Mpa.builder()
                .id(row.getInt("id"))
                .name(row.getString("NAME"))
                .description(row.getString("DESCRIPTION"))
                .build();
    }
}
