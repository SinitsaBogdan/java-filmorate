package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionServiceFilmorate;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.StorageUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceFilmore.SERVICE_ERROR_USER_NOT_IN_FRIENDS_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.*;

@Slf4j
@Service
public class ServiceUser {

    private final StorageUser storage;

    @Autowired
    public ServiceUser(StorageUser storage) {
        this.storage = storage;
    }

    public User getUser(Long userId) {
        return storage.getUser(userId);
    }

    public User createUser(User user) {
        checkValidEmailContainsStorage(storage, user.getEmail(), VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS);
        checkValidUser(user);
        storage.addUser(user);
        log.info("Добавлен новый пользователь!");
        return user;
    }

    public User updateUser(User user) {
        checkValidUser(user);
        storage.updateUser(user);
        log.info("Пользователь {} успешно обновлен!", user.getName());
        return user;
    }

    public Collection<User> getAllUsers() {
        return storage.getUser();
    }

    public void addFriend(Long friendId, Long userId) {
        User user = storage.getUser(userId);
        User friends = storage.getUser(friendId);

        checkValidUser(user);
        checkValidUser(friends);

        if (!user.getFriends().contains(friends.getId())) {
            user.addFriend(friends);
        }
        if (!friends.getFriends().contains(user.getId())) {
            friends.addFriend(user);
        }
    }

    public List<User> getFriends(Long id) {
        return storage.getUser(id).getFriends().stream()
                .map(storage::getUser)
                .collect(Collectors.toList());
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = storage.getUser(userId);
        User friend = storage.getUser(friendId);

        if (user.getFriends().contains(friendId) && friend.getFriends().contains(userId)) {
            user.removeFriend(friend);
            friend.removeFriend(user);
        } else {
            throw new ExceptionServiceFilmorate(SERVICE_ERROR_USER_NOT_IN_FRIENDS_COLLECTIONS);
        }

    }

    public List<User> getFriendsCommon(Long userId, Long otherId) {
        User user = storage.getUser(userId);
        User other = storage.getUser(otherId);
        return  user.getFriends().stream()
                .filter(other.getFriends()::contains)
                .map(storage::getUser)
                .collect(Collectors.toList());
    }

    public void clearStorage() {
        storage.clear();
        log.info("Очистка хранилища Пользователей!");
    }
}
