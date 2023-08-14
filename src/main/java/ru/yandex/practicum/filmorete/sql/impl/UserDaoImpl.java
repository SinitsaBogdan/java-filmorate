package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@Component
@Qualifier("UserDaoImpl")
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Long> findLastIdUser() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT MAX(ID) AS LAST_ID FROM USERS;"
        );
        if (row.next()) return Optional.of(row.getLong("LAST_ID"));
        else return Optional.empty();
    }

    @Override
    public List<User> findAllUsers() {
        List<User> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<User> findUser(Long userId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS WHERE ID = ?;",
                userId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String email) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS WHERE EMAIL = ?;",
                email
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(Long id, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                "INSERT INTO USERS (ID, NAME, BIRTHDAY, LOGIN, EMAIL) VALUES (?, ?, ?, ?, ?);",
                id, name, birthday, login, email
        );
    }

    @Override
    public void insert(String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                "INSERT INTO USERS (NAME, BIRTHDAY, LOGIN, EMAIL) VALUES (?, ?, ?, ?);",
                name, birthday, login, email
        );
    }

    @Override
    public void update(Long rowId, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                "UPDATE USERS SET NAME = ?, BIRTHDAY = ?, LOGIN = ?, EMAIL = ? WHERE  ID = ?;",
                name, birthday, login, email, rowId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM USERS;"
        );
    }

    @Override
    public void delete(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM USERS WHERE ID = ?;",
                rowId
        );
    }

    @Override
    public void delete(String login) {
        jdbcTemplate.update(
                "DELETE FROM USERS WHERE LOGIN = ?;",
                login
        );
    }

    protected User buildModel(@NotNull SqlRowSet row) {
        return User.builder()
                .id(row.getLong("id"))
                .name(row.getString("name"))
                .birthday(Objects.requireNonNull(row.getDate("birthday")).toLocalDate())
                .login(row.getString("login"))
                .email(row.getString("email"))
                .build();
    }
}
