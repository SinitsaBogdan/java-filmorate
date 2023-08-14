package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalUserFriendsDao {

    List<User> findFriendsByUser(Long userId);

    List<User> findFriendsCommon(Long userId, Long friendId);

    List<TotalUserFriends> findRows();

    List<TotalUserFriends> findRowsByUserId(Long userId);

    List<TotalUserFriends> findRowsByFriendId(Long friendId);

    List<TotalUserFriends> findRowsByStatusId(Integer statusId);

    Optional<TotalUserFriends> findRow(Long userId, Long friendId);

    void insert(Long userId, Long friendId, Integer statusId);

    void update(Long searchUserId, Long searchFriendId, Integer statusId);

    void delete();

    void delete(Long userId, Long friendId);

    void deleteAllUserId(Long userId);

    void deleteAllFriendId(Long friendId);

    void deleteAllStatusId(Integer statusId);
}
