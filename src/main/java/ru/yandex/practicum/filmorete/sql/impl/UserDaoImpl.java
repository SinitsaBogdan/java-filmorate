package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;


    public Optional<Long> findLastIdUser() {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT MAX(id) AS last_id FROM USERS;"
        );
        if (row.next()) return Optional.of(row.getLong("last_id"));
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
                "SELECT * FROM USERS WHERE id = ?;",
                userId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String email) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS WHERE email = ?;",
                email
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(Long id, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                "INSERT INTO USERS (id, name, birthday, login, email) VALUES (?, ?, ?, ?, ?);",
                id, name, birthday, login, email
        );
    }

    @Override
    public void insert(String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                "INSERT INTO USERS (name, birthday, login, email) VALUES (?, ?, ?, ?);",
                name, birthday, login, email
        );
    }

    @Override
    public void update(Long rowId, String name, LocalDate birthday, String login, String email) {
        jdbcTemplate.update(
                "UPDATE USERS SET name = ?, birthday = ?, login = ?, email = ? WHERE  id = ?;",
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
                "DELETE FROM USERS WHERE id = ?;",
                rowId
        );
    }

    @Override
    public void delete(String login) {
        jdbcTemplate.update(
                "DELETE FROM USERS WHERE login = ?;",
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
