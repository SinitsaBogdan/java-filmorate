package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.RosterMpaDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.sql.requests.RosterMpaRequests.*;

@Component
@RequiredArgsConstructor
public class RosterMpaDaoImpl implements RosterMpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllName() {
        List<String> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__ROSTER_MPA__NAME.getSql());
        while (row.next()) result.add(row.getString("name"));
        return result;
    }

    @Override
    public List<String> findAllDescription() {
        List<String> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__ROSTER_MPA__DESCRIPTION.getSql());
        while (row.next()) result.add(row.getString("description"));
        return result;
    }

    @Override
    public List<Mpa> findAllMpa() {
        List<Mpa> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__ROSTER_MPA.getSql());
        while (row.next()) result.add(FactoryModel.buildMpa(row));
        return result;
    }

    @Override
    public Optional<Mpa> findMpaById(Integer id) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ONE__ROSTER_MPA__ID.getSql(),
                id
        );
        if (row.next()) return Optional.of(FactoryModel.buildMpa(row));
        else return Optional.empty();
    }

    @Override
    public Optional<Mpa> findMpaByName(String name) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__ROSTER_MPA__NAME.getSql(), name);
        if (row.next()) return Optional.of(FactoryModel.buildMpa(row));
        else return Optional.empty();
    }

    @Override
    public void insert(String name, String description) {
        jdbcTemplate.update(INSERT_ONE__ROSTER_MPA_FULL.getSql(), name, description);
    }

    @Override
    public void insert(Integer id, String name, String description) {
        jdbcTemplate.update(
                INSERT_ONE__ROSTER_MPA_FULL__ID.getSql(),
                id, name, description
        );
    }

    @Override
    public void update(Integer searchId, String name, String description) {
        jdbcTemplate.update(
                UPDATE_ONE__ROSTER_MPA__SET_NAME_DESCRIPTION__ID.getSql(),
                name, description, searchId
        );
    }

    @Override
    public void update(String searchName, String name, String description) {
        jdbcTemplate.update(
                UPDATE_ONE__ROSTER_MPA__SET_NAME_DESCRIPTION__NAME.getSql(),
                name, description, searchName
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(DELETE_ALL__ROSTER_MPA.getSql());
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_ONE__ROSTER_MPA__ID.getSql(), id);
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update(DELETE_ONE__ROSTER_MPA__NAME.getSql(), name);
    }
}