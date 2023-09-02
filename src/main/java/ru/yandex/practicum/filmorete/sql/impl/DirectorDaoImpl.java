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

import static ru.yandex.practicum.filmorete.sql.requests.DirectorRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectorDaoImpl implements DirectorDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Director> find() {
        List<Director> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__DIRECTOR.getSql());
        while (rows.next()) result.add(FactoryModel.buildDirector(rows));
        return result;
    }

    @Override
    public Optional<Director> find(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__DIRECTOR__ID.getSql(), rowId);
        if (row.next()) return Optional.of(FactoryModel.buildDirector(row));
        else return Optional.empty();
    }

    @Override
    public Long insert(String name) {
        jdbcTemplate.update(INSERT_ONE__DIRECTOR__NAME.getSql(), name);
        return jdbcTemplate.queryForObject(SELECT_MAX_ID__DIRECTOR.getSql(), Long.class);
    }

    @Override
    public void insert(Long rowId, String name) {
        jdbcTemplate.update(INSERT_ONE__DIRECTOR__FULL.getSql(), rowId, name);
    }

    @Override
    public void update(Long id, String name) {
        jdbcTemplate.update(UPDATE_ONE__DIRECTOR__SET_NAME__ID.getSql(), name, id);
    }

    @Override
    public void delete() {
        jdbcTemplate.update(DELETE_ALL__DIRECTOR.getSql());
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(DELETE_ONE__DIRECTOR__ID.getSql(), rowId);
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update(DELETE_ONE__DIRECTOR__NAME.getSql(), name);
    }
}
