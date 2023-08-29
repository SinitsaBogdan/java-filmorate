package ru.yandex.practicum.filmorate.sql.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.TotalUserFriends;
import ru.yandex.practicum.filmorate.sql.dao.TotalUserFriendsDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;


@Slf4j
@Component
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
                    "WHERE id IN (" +
                        "SELECT friend_id FROM TOTAL_USER_FRIENDS " +
                        "WHERE user_id = ? AND status_id = 2" +
                    ") " +
                    "ORDER BY id ASC;",
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
                    "WHERE id IN (" +
                        "SELECT friend_id " +
                        "FROM TOTAL_USER_FRIENDS " +
                        "WHERE user_id = ? AND status_id = 2 AND friend_id IN (" +
                            "SELECT friend_id " +
                            "FROM TOTAL_USER_FRIENDS " +
                            "WHERE user_id = ? AND status_id = 2" +
                        ")" +
                    ");",
                userId, friendId
        );
        while (rows.next()) users.add(userDao.buildModel(rows));
        return users;
    }

    @Override
    public List<TotalUserFriends> findAllTotalUserFriend() {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllTotalFriendByUserId(Long userId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE user_id = ?;",
                userId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllTotalUserByFriendId(Long friendId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE friend_id = ?;",
                friendId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllTotalByStatusId(Integer statusId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE status_id = ?;",
                statusId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<TotalUserFriends> findTotalUserFriend(Long userId, Long friendId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS WHERE user_id = ? AND friend_id = ?;",
                userId, friendId
        );
        if (rows.next()) return Optional.of(buildModel(rows));
        else return Optional.empty();
    }

    @Override
    public void insert(Long userId, Long friendId, Integer statusId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_USER_FRIENDS (user_id, friend_id, status_id) VALUES(?, ?, ?);",
                userId, friendId, statusId
        );
    }

    @Override
    public void update(Long searchUserId, Long searchFriendId, Integer statusId) {
        jdbcTemplate.update(
                "UPDATE TOTAL_USER_FRIENDS SET status_id = ? WHERE user_id = ? AND friend_id = ?;",
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
                "DELETE FROM TOTAL_USER_FRIENDS WHERE user_id = ? AND friend_id = ?;",
                userId, friendId
        );
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE user_id = ?;",
                userId
        );
    }

    @Override
    public void deleteAllFriendId(Long friendId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE friend_id = ?;",
                friendId
        );
    }

    @Override
    public void deleteAllStatusId(Integer statusId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS WHERE status_id = ?;",
                statusId
        );
    }

    protected TotalUserFriends buildModel(@NotNull SqlRowSet row) {
        return TotalUserFriends.builder()
                .userId(row.getLong("user_id"))
                .friendId(row.getLong("friend_id"))
                .statusId(row.getLong("status_id"))
                .build();
    }
}
