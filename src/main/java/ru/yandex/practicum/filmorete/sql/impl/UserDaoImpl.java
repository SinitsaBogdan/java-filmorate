package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.sql.requests.UserRequests.*;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Long> findLastIdUser() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_MAX_ID__ID.getSql());
        if (row.next()) return Optional.of(row.getLong("last_id"));
        else return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__USERS.getSql());
        while (row.next()) result.add(FactoryModel.buildUser(row));
        return result;
    }

    @Override
    public Optional<User> findById(Long userId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__USER__ID.getSql(), userId);
        if (row.next()) return Optional.of(FactoryModel.buildUser(row));
        else return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__USER__EMAIL.getSql(), email);
        if (row.next()) return Optional.of(FactoryModel.buildUser(row));
        else return Optional.empty();
    }

    @Override
    public void insert(Long id, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                INSERT_ONE__USER__FULL.getSql(),
                id, name, birthday, login, email
        );
    }

    @Override
    public void insert(String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                INSERT_ONE__USER__NAME_BIRTHDAY_LOGIN_EMAIL.getSql(),
                name, birthday, login, email
        );
    }

    @Override
    public void update(Long rowId, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                UPDATE_ONE__USER__SET_NAME_BIRTHDAY_LOGIN_EMAIL__ID.getSql(),
                name, birthday, login, email, rowId
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL__USERS.getSql());
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(DELETE_ONE__USER__ID.getSql(), rowId);
    }

    @Override
    public void deleteByLogin(String login) {
        jdbcTemplate.update(DELETE_ONE__USER__LOGIN.getSql(), login);
    }
}
