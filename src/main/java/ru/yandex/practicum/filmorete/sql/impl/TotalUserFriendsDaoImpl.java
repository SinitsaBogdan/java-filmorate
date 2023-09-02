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

import static ru.yandex.practicum.filmorete.sql.requests.TotalUserFriendsRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalUserFriendsDaoImpl implements TotalUserFriendsDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll(Long userId) {
        List<User> users = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__USERS__ID.getSql(), userId);
        while (row.next()) users.add(FactoryModel.buildUser(row));
        return users;
    }

    @Override
    public List<User> findFriendsCommon(Long userId, Long friendId) {
        List<User> users = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__USER_COMMON_FRIENDS__USER_FRIEND.getSql(), userId, friendId);
        while (row.next()) users.add(FactoryModel.buildUser(row));
        return users;
    }

    @Override
    public List<TotalUserFriends> findAll() {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_USER_FRIENDS.getSql());
        while (row.next()) result.add(FactoryModel.buildTotalUserFriends(row));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllIsUser(Long userId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_USER_FRIENDS__USER.getSql(), userId);
        while (row.next()) result.add(FactoryModel.buildTotalUserFriends(row));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAllIsFriend(Long friendId) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_USER_FRIENDS__FRIEND.getSql(), friendId);
        while (row.next()) result.add(FactoryModel.buildTotalUserFriends(row));
        return result;
    }

    @Override
    public List<TotalUserFriends> findAll(StatusFriend status) {
        List<TotalUserFriends> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__TOTAL_USER_FRIENDS__STATUS.getSql(),
                status.toString()
        );
        while (row.next()) result.add(FactoryModel.buildTotalUserFriends(row));
        return result;
    }

    @Override
    public Optional<TotalUserFriends> find(Long userId, Long friendId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ONE__TOTAL_USER_FRIENDS__USER_FRIEND.getSql(),
                userId, friendId
        );
        if (row.next()) return Optional.of(FactoryModel.buildTotalUserFriends(row));
        else return Optional.empty();
    }

    @Override
    public void insert(Long userId, Long friendId, @NotNull StatusFriend status) {
        jdbcTemplate.update(
                INSERT_ONE__TOTAL_USER_FRIENDS__USER_FRIEND_STATUS.getSql(),
                userId, friendId, status.toString()
        );
    }

    @Override
    public void update(Long searchUserId, Long searchFriendId, StatusFriend status) {
        jdbcTemplate.update(
                UPDATE_ONE_TOTAL_USER_FRIENDS__SET_STATUS__USER_FRIEND.getSql(),
                status.toString(), searchUserId, searchFriendId
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL__TOTAL_USER_FRIENDS.getSql());
    }

    @Override
    public void deleteAll(Long userId, Long friendId) {
        jdbcTemplate.update(DELETE_ONE__TOTAL_USER_FRIENDS__USER_FRIEND.getSql(), userId, friendId);
    }

    @Override
    public void deleteAllUserId(Long userId) {
        jdbcTemplate.update(DELETE_ALL__TOTAL_USER_FRIENDS__USER.getSql(), userId);
    }

    @Override
    public void deleteAllFriendId(Long friendId) {
        jdbcTemplate.update(DELETE_ALL__TOTAL_USER_FRIENDS__FRIEND.getSql(), friendId);
    }

    @Override
    public void deleteAll(StatusFriend status) {
        jdbcTemplate.update(DELETE_ALL__TOTAL_USER_FRIENDS__STATUS.getSql(), status.toString());
    }
}
