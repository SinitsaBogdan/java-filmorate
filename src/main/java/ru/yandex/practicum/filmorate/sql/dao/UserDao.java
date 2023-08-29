package ru.yandex.practicum.filmorate.sql.dao;

import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAllUsers();

    Optional<User> findUser(Long rowId);

    Optional<User> findUser(String email);

    void insert(String name, LocalDate birthday, String login, String email);

    void insert(Long rowId, String name, LocalDate birthday, String login, String email);

    void update(Long rowId, String name, LocalDate birthday, String login, String email);

    void delete();

    void delete(Long rowId);

    void delete(String login);
}
