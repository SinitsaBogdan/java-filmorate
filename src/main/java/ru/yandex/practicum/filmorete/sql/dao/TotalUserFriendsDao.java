package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalUserFriendsDao {

    List<TotalUserFriends> findAll();

    List<TotalUserFriends> findAll(StatusFriend statusFriend);

    List<TotalUserFriends> findAllIsUser(Long userId);

    List<TotalUserFriends> findAllIsFriend(Long friendId);

    Optional<TotalUserFriends> find(Long userId, Long friendId);

    List<User> findAll(Long userId);

    List<User> findFriendsCommon(Long userId, Long friendId);

    void insert(Long userId, Long friendId, StatusFriend status);

    void update(Long searchUserId, Long searchFriendId, StatusFriend status);

    void deleteAll();

    void deleteAll(Long userId, Long friendId);

    void deleteAll(StatusFriend status);

    void deleteAllUserId(Long userId);

    void deleteAllFriendId(Long friendId);
}
