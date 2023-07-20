package ru.yandex.practicum.filmorete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public List<User> getFriends(User user) {
        return user.getFriends().stream()
                .map(storage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getSharedFriends(User user1, User user2) {
        return user1.getFriends().stream()
                .filter(user2.getFriends()::contains)
                .map(storage::getUser)
                .collect(Collectors.toList());
    }

    public void addFriend(User user, User friends) {
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }

        if (friends.getFriends() == null) {
            friends.setFriends(new HashSet<>());
        }

        if (!user.getFriends().contains(friends.getId())) {
            user.addFriend(friends);
        }
        if (!friends.getFriends().contains(user.getId())) {
            friends.addFriend(user);
        }
    }

    public void removeFriend(User user, User friends) {
        user.removeFriend(friends);
        friends.removeFriend(user);
    }
}
