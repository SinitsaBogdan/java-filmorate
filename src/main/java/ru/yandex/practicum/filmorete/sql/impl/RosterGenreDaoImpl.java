package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;

import java.util.*;


@Slf4j
@Component
@Primary
@Qualifier("RosterGenreDaoImpl")
public class RosterGenreDaoImpl implements RosterGenreDao {

    private final JdbcTemplate jdbcTemplate;

    private RosterGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Integer> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT MAX(ID) AS LAST_ID FROM ROSTER_GENRE;"
        );
        return Optional.of(row.getInt("LAST_ID"));
    }

    @Override
    public Optional<List<String>> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT NAME FROM ROSTER_GENRE;"
        );
        while (rows.next()) {
            result.add(rows.getString("NAME"));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<Genre>> findRows() {
        List<Genre> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE ORDER BY ID ASC;"
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<Genre> findRow(Integer rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE WHERE ID = ?;",
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public Optional<Genre> findRow(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_GENRE WHERE NAME = ?;",
                name
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_GENRE (NAME) VALUES(?);",
                name
        );
    }

    @Override
    public void insert(Integer rowId, String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_GENRE (ID, NAME) VALUES(?, ?);",
                rowId, name
        );
    }

    @Override
    public void update(Integer searchRowId, String name) {
        jdbcTemplate.update(
                "UPDATE ROSTER_GENRE SET NAME = ? WHERE ID = ?;",
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
                    "WHERE ID = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_GENRE " +
                    "WHERE NAME = ?;",
                name
        );
    }

    protected Genre buildModel(@NotNull SqlRowSet row) {
        return Genre.builder()
                .id(row.getInt("ID"))
                .name(row.getString("NAME"))
                .build();
    }
}
