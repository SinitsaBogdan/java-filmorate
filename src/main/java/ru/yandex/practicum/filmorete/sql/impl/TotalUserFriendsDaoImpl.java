package ru.yandex.practicum.filmorete.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.sql.dao.TotalUserFriendsDao;
import ru.yandex.practicum.filmorete.model.User;

import java.util.*;


@Slf4j
@Component
@Primary
@Qualifier("TotalUserFriendsDaoImpl")
public class TotalUserFriendsDaoImpl implements TotalUserFriendsDao {

    private final JdbcTemplate jdbcTemplate;

    private final UserDaoImpl userDao;

    private TotalUserFriendsDaoImpl(JdbcTemplate jdbcTemplate, UserDaoImpl userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<User> findFriendsByUser(Long userId) {
        List<User> users = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS " +
                    "WHERE ID IN (" +
                        "SELECT FRIEND_ID FROM TOTAL_USER_FRIENDS " +
                        "WHERE USER_ID = ? AND STATUS_ID = 2" +
                    ") " +
                    "ORDER BY ID ASC;",
                userId
        );
        while (rows.next()) users.add(userDao.buildModel(rows));
        return users;
    }

    @Override
    public List<User> findFriendsCommon(Long userId, Long friendId) {
        List<User> users = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS " +
                    "WHERE ID IN (" +
                        "SELECT FRIEND_ID " +
                        "FROM TOTAL_USER_FRIENDS " +
                        "WHERE USER_ID = ? AND STATUS_ID = 2 AND FRIEND_ID IN (" +
                            "SELECT FRIEND_ID " +
                            "FROM TOTAL_USER_FRIENDS " +
                            "WHERE USER_ID = ? AND STATUS_ID = 2" +
                        ")" +
                    ");",
                userId, friendId
        );
        while (rows.next()) users.add(userDao.buildModel(rows));
        return users;
    }

    @Override
    public List<TotalUserFriends> findRows() {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findRowsByUserId(Long userId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE USER_ID = ?;",
                userId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findRowsByFriendId(Long friendId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE FRIEND_ID = ?;",
                friendId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findRowsByStatusId(Integer statusId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE STATUS_ID = ?;",
                statusId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<TotalUserFriends> findRow(Long userId, Long friendId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?;",
                userId, friendId
        );
        if (rows.next()) return Optional.of(buildModel(rows));
        else return Optional.empty();
    }

    @Override
    public void insert(Long userId, Long friendId, Integer statusId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_USER_FRIENDS (USER_ID, FRIEND_ID, STATUS_ID) VALUES(?, ?, ?);",
                userId, friendId, statusId
        );
    }

    @Override
    public void update(Long searchUserId, Long searchFriendId, Integer statusId) {
        jdbcTemplate.update(
                "UPDATE TOTAL_USER_FRIENDS SET STATUS_ID = ? WHERE USER_ID = ? AND FRIEND_ID = ?;",
                statusId, searchUserId, searchFriendId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS;"
        );
    }

    @Override
    public void delete(Long userId, Long friendId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?;",
                userId, friendId
        );
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE USER_ID = ?;",
                userId
        );
    }

    @Override
    public void deleteAllFriendId(Long friendId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE FRIEND_ID = ?;",
                friendId
        );
    }

    @Override
    public void deleteAllStatusId(Integer statusId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE STATUS_ID = ?;",
                statusId
        );
    }

    protected TotalUserFriends buildModel(@NotNull SqlRowSet row) {
        return TotalUserFriends.builder()
                .userId(row.getLong("USER_ID"))
                .friendId(row.getLong("FRIEND_ID"))
                .statusId(row.getLong("STATUS_ID"))
                .build();
    }
}
