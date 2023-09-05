package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.sql.requests.RosterGenreRequests.*;

@Component
@RequiredArgsConstructor
public class RosterGenreDaoImpl implements RosterGenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllColumnName() {
        List<String> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__ROSTER_GENRE__COLUMN_NAME.getSql());
        while (row.next()) result.add(row.getString("name"));
        return result;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__ROSTER_GENRE__NAME.getSql());
        while (row.next()) result.add(FactoryModel.buildGenre(row));
        return result;
    }

    @Override
    public Optional<Genre> findAllByRowId(Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__ROSTER_GENRE.getSql(), id);
        if (row.next()) return Optional.of(FactoryModel.buildGenre(row));
        else return Optional.empty();
    }

    @Override
    public Optional<Genre> findAllByName(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__ROSTER_GENRE__NAME.getSql(), name);
        if (row.next()) return Optional.of(FactoryModel.buildGenre(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name) {
        jdbcTemplate.update(INSERT_ONE__ROSTER_GENRE__NAME.getSql(), name);
    }

    @Override
    public void insert(Integer id, String name) {
        jdbcTemplate.update(INSERT_ONE__ROSTER_GENRE__FULL.getSql(), id, name);
    }

    @Override
    public void update(Integer searchId, String name) {
        jdbcTemplate.update(UPDATE_ONE__ROSTER_GENRE__SET_NAME__ID.getSql(), name, searchId);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL__ROSTER_GENRE.getSql());
    }

    @Override
    public void deleteAllById(Integer id) {
        jdbcTemplate.update(DELETE_ONE__ROSTER_GENRE__ID.getSql(), id);
    }

    @Override
    public void deleteAllByName(String name) {
        jdbcTemplate.update(DELETE_ONE__ROSTER_GENRE__NAME.getSql(), name);
    }
}