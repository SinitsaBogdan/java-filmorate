package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import java.util.List;
import java.util.Optional;

public interface TotalUserFriendsDao {

    List<User> findFriendsByUser(Long userId);

    List<User> findFriendsCommon(Long userId, Long friendId);

    List<TotalUserFriends> findAllTotalUserFriend();

    List<TotalUserFriends> findAllTotalFriendByUserId(Long userId);

    List<TotalUserFriends> findAllTotalUserByFriendId(Long friendId);

    List<TotalUserFriends> findAllTotalByStatusId(StatusFriend statusFriend);

    Optional<TotalUserFriends> findTotalUserFriend(Long userId, Long friendId);

    void insert(Long userId, Long friendId, StatusFriend status);

    void update(Long searchUserId, Long searchFriendId, StatusFriend status);

    void delete();

    void delete(Long userId, Long friendId);

    void deleteAllUserId(Long userId);

    void deleteAllFriendId(Long friendId);

    void deleteAllStatusId(StatusFriend status);
}
