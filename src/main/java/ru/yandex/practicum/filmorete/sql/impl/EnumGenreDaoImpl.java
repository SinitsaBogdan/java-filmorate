package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.EnumGenreDao;

import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableEnumGenre.*;

@Slf4j
@Component
@Primary
@Qualifier("EnumGenreDaoImpl")
public class EnumGenreDaoImpl implements EnumGenreDao {

    private final JdbcTemplate jdbcTemplate;

    private Genre buildModel(SqlRowSet row) {
        return Genre.builder()
                .id(row.getInt("ID"))
                .name(row.getString("NAME"))
                .build();
    }

    private EnumGenreDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Optional<Integer> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_GENRE__LAST_ID.getTemplate()
        );
        return Optional.of(row.getInt("LAST_ID"));
    }

    @Override
    public Optional<List<String>> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_GENRE__ALL_NAME.getTemplate()
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
                SELECT_TABLE_ENUM_GENRE__ALL_ROWS.getTemplate()
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<Genre> findRow(Integer rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_GENRE__ROW_BY_ID.getTemplate(),
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public Optional<Genre> findRow(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_ENUM_GENRE__ROW_BY_NAME.getTemplate(),
                name
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(
                INSERT_TABLE_ENUM_GENRE.getTemplate(),
                name
        );
    }

    @Override
    public void insert(Integer rowId, String name) {
        jdbcTemplate.update(
                INSERT_TABLE_ENUM_GENRE__ALL_COLUMN.getTemplate(),
                rowId, name
        );
    }

    @Override
    public void update(Integer searchRowId, String name) {
        jdbcTemplate.update(
                UPDATE_TABLE_ENUM_GENRE__ROW_BY_ID.getTemplate(),
                name, searchRowId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_ENUM_GENRE__ALL_ROWS.getTemplate()
        );

    }

    @Override
    public void delete(Integer rowId) {
        jdbcTemplate.update(
                DELETE_TABLE_ENUM_GENRE__ROW_BY_ID.getTemplate(),
                rowId
        );
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(
                DELETE_TABLE_ENUM_GENRE__ROW_BY_NAME.getTemplate(),
                name
        );
    }
}
