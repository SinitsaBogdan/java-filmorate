package ru.yandex.practicum.filmorete.storage;

import ru.yandex.practicum.filmorete.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Long getLastIdentification();

    Set<Long> getCollectionsIdUsers();

    Collection<User> getUser();

    User getUser(Long id);

    void addUser(User user);

    void updateUser(User user);

    User removeUser(User user);

    Set<String> getEmails();

    void addEmail(String email);

    Boolean removeEmail(String email);

    void clear();
}
