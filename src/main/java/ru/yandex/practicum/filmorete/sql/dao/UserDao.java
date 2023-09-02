package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll();

    Optional<User> find(Long rowId);

    Optional<User> find(String email);

    void insert(String name, LocalDate birthday, String login, String email);

    void insert(Long rowId, String name, LocalDate birthday, String login, String email);

    void update(Long rowId, String name, LocalDate birthday, String login, String email);

    void deleteAll();

    void deleteAll(Long rowId);

    void deleteAll(String login);
}
