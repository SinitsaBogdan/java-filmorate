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

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableRosterMpa.*;

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
    public Optional<Long> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ROSTER_MPA__LAST_ID.getTemplate()
        );
        return Optional.of(row.getLong("LAST_ID"));
    }

    @Override
    public Optional<List<String>> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ROSTER_MPA__ALL_NAME.getTemplate()
        );
        while (rows.next()) {
            result.add(rows.getString("NAME"));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<String>> findAllDescription() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ROSTER_MPA__ALL_DESCRIPTIONS.getTemplate()
        );
        while (rows.next()) {
            result.add(rows.getString("DESCRIPTION"));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<Mpa>> findRows()  {
        List<Mpa> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ROSTER_MPA__ALL_ROWS.getTemplate()
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<Mpa> findRow(Integer rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ROSTER_MPA__ROW_BY_ID.getTemplate(),
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public Optional<Mpa> findRow(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ROSTER_MPA__ROW_BY_NAME.getTemplate(),
                name
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public void insert(String name, String description) {
        jdbcTemplate.update(
                INSERT_TABLE_ROSTER_MPA.getTemplate(),
                name, description
        );
    }

    @Override
    public void insert(Integer rowId, String name, String description) {
        jdbcTemplate.update(
                INSERT_TABLE_ROSTER_MPA__ALL_COLUMN.getTemplate(),
                rowId, name, description
        );
    }

    @Override
    public void update(Integer searchRowId, String name, String description) {
        jdbcTemplate.update(
                UPDATE_TABLE_ROSTER_MPA__ROW_BY_ID.getTemplate(),
                name, description, searchRowId
        );
    }

    @Override
    public void update(String searchName, String name, String description) {
        jdbcTemplate.update(
                UPDATE_TABLE_ROSTER_MPA__ROW_BY_NAME.getTemplate(),
                name, description, searchName
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_ROSTER_MPA__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Integer rowId) {
        jdbcTemplate.update(
                DELETE_TABLE_ROSTER_MPA__ROW_BY_ID.getTemplate(),
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                DELETE_TABLE_ROSTER_MPA__ROW_BY_NAME.getTemplate(),
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
