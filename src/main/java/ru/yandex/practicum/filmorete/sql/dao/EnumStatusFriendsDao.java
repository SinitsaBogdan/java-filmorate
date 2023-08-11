package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.StatusFriends;
import java.util.List;
import java.util.Optional;

public interface EnumStatusFriendsDao {

    Optional<Long> findLastId();

    Optional<List<String>> findAllName();

    Optional<List<StatusFriends>> findRows();
    Optional<StatusFriends> findRow(Long rowId);
    Optional<StatusFriends> findRow(String status);

    void insert(String status);
    void insert(Long rowId, String status);

    void update(Long searchRowId, String status);

    void delete();
    void delete(Long rowId);
    void delete(String status);
}
