package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;
import static ru.yandex.practicum.filmorete.service.Validators.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public User getUser(Long userId) {
        checkValidContainsStorage(storage, userId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        return storage.getUser(userId);
    }

    public User createUser(User user) {
        checkValidEmailContainsStorage(storage, user.getEmail(), VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS);
        Validators.checkValidLogin(user.getLogin());
        checkValidUser(user);
        storage.addUser(user);
        log.info("Добавлен новый пользователь!");
        return user;
    }

    public User updateUser(User user) {
        checkValidIdNotNul(user.getId(), VALID_ERROR_USER_NOT_ID);
        checkValidContainsStorage(storage, user.getId(), VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        checkValidUser(user);
        checkValidLogin(user.getLogin());
        storage.updateUser(user);
        log.info("Пользователь {} успешно обновлен!", user.getName());
        return user;
    }

    public Collection<User> getAllUsers() {
        return storage.getUser();
    }

    public void addFriend(Long friendId, Long userId) {
        checkValidContainsStorage(storage, friendId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        checkValidContainsStorage(storage, userId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);

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
        checkValidContainsStorage(storage, friendId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        checkValidContainsStorage(storage, userId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        User user = storage.getUser(userId);
        User friend = storage.getUser(friendId);
        user.removeFriend(friend);
        friend.removeFriend(user);
    }

    public List<User> getFriendsCommon(Long userId, Long otherId) {
        checkValidContainsStorage(storage, userId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        checkValidContainsStorage(storage, otherId, VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
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
