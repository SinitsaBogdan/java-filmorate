package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<Long> findLastId();

    Optional<List<User>> findRows();

    Optional<User> findRow(Long rowId);

    Optional<User> findRow(String email);

    void insert(String name, LocalDate birthday, String login, String email);

    void insert(Long rowId, String name, LocalDate birthday, String login, String email);

    void update(Long rowId, String name, LocalDate birthday, String login, String email);

    void delete();

    void delete(Long rowId);

    void delete(String login);
}
