package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalUserFriendsDao {

    List<TotalUserFriends> findAll();

    List<TotalUserFriends> findAllByStatusFriend(StatusFriend statusFriend);

    List<TotalUserFriends> findAllIsUserByUserId(Long userId);

    List<TotalUserFriends> findAllFriendByFriendId(Long friendId);

    Optional<TotalUserFriends> findByUserIdAndFriendId(Long userId, Long friendId);

    List<User> findAllByUserId(Long userId);

    List<User> findFriendsCommon(Long userId, Long friendId);

    void insert(Long userId, Long friendId, StatusFriend status);

    void update(Long searchUserId, Long searchFriendId, StatusFriend status);

    void deleteAll();

    void deleteAllByUserIdAndFriendId(Long userId, Long friendId);

    void deleteAllByStatusFriend(StatusFriend status);

    void deleteAllUserId(Long userId);

    void deleteAllFriendId(Long friendId);
}
