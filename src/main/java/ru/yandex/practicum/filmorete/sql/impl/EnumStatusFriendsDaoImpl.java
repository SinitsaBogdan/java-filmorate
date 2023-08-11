package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.StatusFriends;
import ru.yandex.practicum.filmorete.sql.dao.EnumStatusFriendsDao;

import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableEnumStatusFriends.*;

@Slf4j
@Component
@Primary
@Qualifier("EnumStatusFriendsDaoImpl")
public class EnumStatusFriendsDaoImpl implements EnumStatusFriendsDao {

    private final JdbcTemplate jdbcTemplate;

    private EnumStatusFriendsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StatusFriends buildModel(@NotNull SqlRowSet row) {
        return StatusFriends.builder()
                .id(row.getLong("id"))
                .status(row.getString("NAME"))
                .build();
    }

    @Override
    public Optional<Long> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_STATUS_FRIENDS__LAST_ID.getTemplate()
        );
        return Optional.of(row.getLong("LAST_ID"));
    }

    @Override
    public Optional<List<String>> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_STATUS_FRIENDS__ALL_NAME.getTemplate()
        );
        while (rows.next()) {
            result.add(rows.getString("NAME"));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<StatusFriends>> findRows() {
        List<StatusFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_STATUS_FRIENDS__ALL_ROWS.getTemplate()
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<StatusFriends> findRow(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_STATUS_FRIENDS__ROW_BY_ID.getTemplate(),
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public Optional<StatusFriends> findRow(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_STATUS_FRIENDS__ROW_BY_NAME.getTemplate(),
                name
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(
                INSERT_TABLE_ENUM_STATUS_FRIENDS.getTemplate(),
                name
        );
    }

    @Override
    public void insert(Long rowId, String name) {
        jdbcTemplate.update(
                INSERT_TABLE_ENUM_STATUS_FRIENDS__ALL_COLUMN.getTemplate(),
                rowId, name
        );
    }

    @Override
    public void update(Long searchRowId, String name) {
        jdbcTemplate.update(
                UPDATE_TABLE_ENUM_STATUS_FRIENDS__ROW_BY_ID.getTemplate(),
                name, searchRowId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_ENUM_STATUS_FRIENDS__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                DELETE_TABLE_ENUM_STATUS_FRIENDS__ROW_BY_ID.getTemplate(),
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                DELETE_TABLE_ENUM_STATUS_FRIENDS__ROW_BY_NAME.getTemplate(),
                name
        );
    }
}
