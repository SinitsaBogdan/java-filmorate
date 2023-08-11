package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;
import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableUsers.*;

@Slf4j
@Component
@Primary
@Qualifier("UserDaoImpl")
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private UserDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public User buildModel(SqlRowSet row) {
        return User.builder()
                .id(row.getLong("id"))
                .name(row.getString("name"))
                .birthday(Objects.requireNonNull(row.getDate("birthday")).toLocalDate())
                .login(row.getString("login"))
                .email(row.getString("email"))
                .build();
    }

    public Optional<Long> findLastId() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_USERS__LAST_ID.getTemplate()
        );
        if (row.next()) {
            return Optional.of(row.getLong("LAST_ID"));
        } else return Optional.empty();
    }

    @Override
    public Optional<List<User>> findRows() {
        List<User> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_USERS__ALL_ROWS.getTemplate()
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<User> findRow(Long userId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_USERS__ROW_BY_ID.getTemplate(),
                userId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public Optional<User> findRow(String email) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_USERS__ROW_BY_EMAIL.getTemplate(),
                email
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else return Optional.empty();
    }

    @Override
    public void insert(Long id, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                INSERT_TABLE_USERS_All_COLUMN.getTemplate(),
                id, name, birthday, login, email
        );
    }

    @Override
    public void insert(String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                INSERT_TABLE_USERS.getTemplate(),
                name, birthday, login, email
        );
    }

    @Override
    public void update(Long rowId, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                UPDATE_TABLE_USERS__ROW_BY_ID.getTemplate(),
                name, birthday, login, email, rowId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_USERS__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                DELETE_TABLE_USERS__ROW_BY_ID.getTemplate(),
                rowId
        );
    }

    @Override
    public void delete(String login) {
        jdbcTemplate.update(
                DELETE_TABLE_USERS__ROW_BY_LOGIN.getTemplate(),
                login
        );
    }
}
