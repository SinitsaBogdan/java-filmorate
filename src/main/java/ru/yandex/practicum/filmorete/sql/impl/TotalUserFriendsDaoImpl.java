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

import static ru.yandex.practicum.filmorete.sql.requests.RequestsTableTotalUserFriends.*;

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
    public Optional<List<User>> findFriendsByUser(Long userId) {
        List<User> users = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL_FRIENDS_BY_USER_ID_ON_MODEL_USER.getTemplate(),
                userId
        );
        while (rows.next()) {
            users.add(userDao.buildModel(rows));
        }
        return Optional.of(users);
    }

    @Override
    public Optional<List<User>> findFriendsCommon(Long userId, Long friendId) {
        List<User> users = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL_FRIENDS_COMMON_BY_USER_ID_AND_FRIEND_ID_ON_MODEL_USER.getTemplate(),
                userId, friendId
        );
        while (rows.next()) {
            users.add(userDao.buildModel(rows));
        }
        return Optional.of(users);
    }

    @Override
    public Optional<List<TotalUserFriends>> findRows() {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS.getTemplate()
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalUserFriends>> findRowsByUserId(Long userId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS_BY_USER_ID.getTemplate(),
                userId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalUserFriends>> findRowsByFriendId(Long friendId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS_BY_FRIEND_ID.getTemplate(),
                friendId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<TotalUserFriends>> findRowsByStatusId(Integer statusId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS_BY_STATUS_ID.getTemplate(),
                statusId
        );
        while (rows.next()) {
            result.add(buildModel(rows));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<TotalUserFriends> findRow(Long userId, Long friendId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_TABLE_TOTAL_USER_FRIENDS__ROW_BY_USER_ID_AND_FRIEND_ID.getTemplate(),
                userId, friendId
        );
        if (rows.next()) {
            return Optional.of(buildModel(rows));
        } else return Optional.empty();
    }

    @Override
    public void insert(Long userId, Long friendId, Integer statusId) {
        jdbcTemplate.update(
                INSERT_TABLE_TOTAL_USER_FRIENDS__ALL_COLUMN.getTemplate(),
                userId, friendId, statusId
        );
    }

    @Override
    public void update(Long searchUserId, Long searchFriendId, Integer statusId) {
        jdbcTemplate.update(
                UPDATE_TABLE_TOTAL_USER_FRIENDS__STATUS_BY_USER_ID_AND_FRIEND_ID.getTemplate(),
                statusId, searchUserId, searchFriendId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS.getTemplate()
        );
    }

    @Override
    public void delete(Long userId, Long friendId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_USER_ID_AND_FRIEND_ID.getTemplate(),
                userId, friendId
        );
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_USER_ID.getTemplate(),
                userId
        );
    }

    @Override
    public void deleteAllFriendId(Long friendId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_FRIEND_ID.getTemplate(),
                friendId
        );
    }

    @Override
    public void deleteAllStatusId(Integer statusId) {
        jdbcTemplate.update(
                DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_STATUS_ID.getTemplate(),
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
