package ru.yandex.practicum.filmorate.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.sql.dao.RosterGenreDao;

import java.util.*;


@Slf4j
@Component
@Qualifier("RosterGenreDaoImpl")
public class RosterGenreDaoImpl implements RosterGenreDao {

    private final JdbcTemplate jdbcTemplate;

    private RosterGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT name FROM ROSTER_GENRE;"
        );
        while (rows.next()) result.add(rows.getString("name"));
        return result;
    }

    @Override
    public List<Genre> findAllGenre() {
        List<Genre> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE ORDER BY id ASC;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<Genre> findGenre(Integer rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE WHERE id = ?;",
                rowId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Optional<Genre> findGenre(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE WHERE name = ?;",
                name
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_GENRE (name) VALUES(?);",
                name
        );
    }

    @Override
    public void insert(Integer rowId, String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_GENRE (id, name) VALUES(?, ?);",
                rowId, name
        );
    }

    @Override
    public void update(Integer searchRowId, String name) {
        jdbcTemplate.update(
                "UPDATE ROSTER_GENRE SET name = ? WHERE id = ?;",
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

    protected Genre buildModel(@NotNull SqlRowSet row) {
        return Genre.builder()
                .id(row.getInt("id"))
                .name(row.getString("name"))
                .build();
    }
}