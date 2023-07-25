package ru.yandex.practicum.filmorete.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidationUser;
import ru.yandex.practicum.filmorete.model.User;

import java.util.*;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_NOT_ID;

@Component
public class InMemoryUserStorage implements StorageUser {

    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private Long lastIdentification = 1L;

    @Override
    public Long getLastIdentification() {
        return lastIdentification++;
    }

    @Override
    public Set<Long> getCollectionsIdUsers() {
        return users.keySet();
    }

    @Override
    public Collection<User> getUser() {
        return users.values();
    }

    @Override
    public User getUser(Long id) {
        if (id == null) {
            throw new ExceptionValidationUser(VALID_ERROR_USER_NOT_ID);
        } else if (users.get(id) == null) {
            throw new ExceptionValidationUser(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        } else {
            return users.get(id);
        }
    }

    @Override
    public void addUser(User user) {
        user.setId(getLastIdentification());
        users.put(user.getId(), user);
        emails.add(user.getEmail());
    }

    @Override
    public void updateUser(User user) {
        User oldUser = getUser(user.getId());
        emails.remove(oldUser.getEmail());
        emails.add(user.getEmail());
        users.put(oldUser.getId(), user);
    }

    @Override
    public User removeUser(Long id) {
        return users.remove(getUser(id).getId());
    }

    @Override
    public Set<String> getEmails() {
        return emails;
    }

    @Override
    public void addEmail(String email) {
       emails.add(email);
    }

    @Override
    public Boolean removeEmail(String email) {
        return emails.remove(email);
    }

    @Override
    public void clear() {
        users.clear();
        emails.clear();
        lastIdentification = 1L;
    }
}
