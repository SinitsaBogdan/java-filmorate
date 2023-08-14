package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.StatusFriends;
import ru.yandex.practicum.filmorete.sql.dao.RosterStatusFriendsDao;

import java.util.*;


@Slf4j
@Component
@Primary
@Qualifier("RosterStatusFriendsDaoImpl")
public class RosterStatusFriendsDaoImpl implements RosterStatusFriendsDao {

    private final JdbcTemplate jdbcTemplate;

    private RosterStatusFriendsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Long> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT MAX(ID) AS LAST_ID FROM ROSTER_STATUS_FRIENDS;"
        );
        return Optional.of(row.getLong("LAST_ID"));
    }

    @Override
    public List<String> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT NAME FROM ROSTER_STATUS_FRIENDS;"
        );
        while (rows.next()) result.add(rows.getString("NAME"));
        return result;
    }

    @Override
    public List<StatusFriends> findRows() {
        List<StatusFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_STATUS_FRIENDS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<StatusFriends> findRow(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_STATUS_FRIENDS WHERE ID = ?;",
                rowId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Optional<StatusFriends> findRow(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_STATUS_FRIENDS WHERE NAME = ?;",
                name
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_STATUS_FRIENDS (NAME) VALUES(?);",
                name
        );
    }

    @Override
    public void insert(Long rowId, String name) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_STATUS_FRIENDS (ID, NAME) VALUES(?, ?);",
                rowId, name
        );
    }

    @Override
    public void update(Long searchRowId, String name) {
        jdbcTemplate.update(
                "UPDATE ROSTER_STATUS_FRIENDS SET NAME = ? WHERE ID = ?;",
                name, searchRowId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_STATUS_FRIENDS;"
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_STATUS_FRIENDS WHERE ID = ?;",
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_STATUS_FRIENDS WHERE NAME = ?;",
                name
        );
    }

    protected StatusFriends buildModel(@NotNull SqlRowSet row) {
        return StatusFriends.builder()
                .id(row.getLong("id"))
                .status(row.getString("NAME"))
                .build();
    }
}
