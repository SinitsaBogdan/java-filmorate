package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.StatusFriends;
import java.util.List;
import java.util.Optional;

public interface RosterStatusFriendsDao {

    List<String> findAllName();

    List<StatusFriends> findAllStatusFriends();

    Optional<StatusFriends> findStatusFriends(Long rowId);

    Optional<StatusFriends> findStatusFriends(String status);

    void insert(String status);

    void insert(Long rowId, String status);

    void update(Long searchRowId, String status);

    void delete();

    void delete(Long rowId);

    void delete(String status);
}
