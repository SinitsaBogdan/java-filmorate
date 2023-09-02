package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.TotalUserFriendsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalUserFriendsDaoImpl implements TotalUserFriendsDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findFriendsByUser(Long userId) {
        List<User> users = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM USERS " +
                    "WHERE id IN (" +
                        "SELECT friend_id FROM TOTAL_USER_FRIENDS " +
                        "WHERE user_id = ? AND status = 'CONFIRMED'" +
                    ") " +
                    "ORDER BY id ASC;",
                userId
        );
        while (rows.next()) users.add(FactoryModel.buildUser(rows));
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
                        "WHERE user_id = ? AND status = 'CONFIRMED' AND friend_id IN (" +
                            "SELECT friend_id " +
                            "FROM TOTAL_USER_FRIENDS " +
                            "WHERE user_id = ? AND status = 'CONFIRMED'" +
                        ")" +
                    ");",
                userId, friendId
        );
        while (rows.next()) users.add(FactoryModel.buildUser(rows));
        return users;
    }

    @Override
    public List<TotalUserFriends> findAllTotalUserFriend() {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS;"
        );
        while (rows.next()) result.add(FactoryModel.buildTotalUserFriends(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllTotalFriendByUserId(Long userId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE user_id = ?;",
                userId
        );
        while (rows.next()) result.add(FactoryModel.buildTotalUserFriends(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllTotalUserByFriendId(Long friendId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE friend_id = ?;",
                friendId
        );
        while (rows.next()) result.add(FactoryModel.buildTotalUserFriends(rows));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllTotalByStatusId(StatusFriend status) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE status = ?;",
                status.toString()
        );
        while (rows.next()) result.add(FactoryModel.buildTotalUserFriends(rows));
        return result;
    }

    @Override
    public Optional<TotalUserFriends> findTotalUserFriend(Long userId, Long friendId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE user_id = ? AND friend_id = ?;",
                userId, friendId
        );
        if (rows.next()) return Optional.of(FactoryModel.buildTotalUserFriends(rows));
        else return Optional.empty();
    }

    @Override
    public void insert(Long userId, Long friendId, @NotNull StatusFriend status) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_USER_FRIENDS (user_id, friend_id, status) " +
                    "VALUES(?, ?, ?);",
                userId, friendId, status.toString()
        );
    }

    @Override
    public void update(Long searchUserId, Long searchFriendId, StatusFriend status) {
        jdbcTemplate.update(
                "UPDATE TOTAL_USER_FRIENDS SET status = ? " +
                    "WHERE user_id = ? AND friend_id = ?;",
                status.toString(), searchUserId, searchFriendId
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
                "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE user_id = ? AND friend_id = ?;",
                userId, friendId
        );
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE user_id = ?;",
                userId
        );
    }

    @Override
    public void deleteAllFriendId(Long friendId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE friend_id = ?;",
                friendId
        );
    }

    @Override
    public void deleteAllStatusId(StatusFriend status) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE status = ?;",
                status.toString()
        );
    }
}
