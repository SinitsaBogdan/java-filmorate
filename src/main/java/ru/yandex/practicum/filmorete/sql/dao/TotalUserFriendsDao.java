package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalUserFriendsDao {

    Optional<List<User>> findFriendsByUser(Long userId);

    Optional<List<User>> findFriendsCommon(Long userId, Long friendId);

    Optional<List<TotalUserFriends>> findRows();

    Optional<List<TotalUserFriends>> findRowsByUserId(Long userId);

    Optional<List<TotalUserFriends>> findRowsByFriendId(Long friendId);

    Optional<List<TotalUserFriends>> findRowsByStatusId(Integer statusId);

    Optional<TotalUserFriends> findRow(Long userId, Long friendId);

    void insert(Long userId, Long friendId, Integer statusId);

    void update(Long searchUserId, Long searchFriendId, Integer statusId);

    void delete();

    void delete(Long userId, Long friendId);

    void deleteAllUserId(Long userId);

    void deleteAllFriendId(Long friendId);

    void deleteAllStatusId(Integer statusId);
}