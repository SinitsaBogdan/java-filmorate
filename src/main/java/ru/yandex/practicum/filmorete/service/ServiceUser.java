package ru.yandex.practicum.filmorete.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalUserFriendsDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_DOUBLE_EMAIL_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.checkValidUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceUser {
    private final UserDao userDao;
    private final TotalUserFriendsDao totalUserFriendsDao;
    private final TotalFilmLikeDao totalFilmLikeDao;
    private final EventsDao eventsDao;

    public User getUser(Long userId) {
        Optional<User> optional = userDao.findUser(userId);
        if (optional.isPresent()) return optional.get();
        else throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
    }

    public User createUser(User user) {
        checkValidUser(user);
        Optional<User> optional = userDao.findUser(user.getEmail());
        if (optional.isEmpty()) {
            userDao.insert(user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
            return userDao.findUser(user.getEmail()).get();
        } else throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_DOUBLE_EMAIL_IN_COLLECTIONS);
    }

    public User updateUser(User user) {
        checkValidUser(user);
        Optional<User> optional = userDao.findUser(user.getId());
        if (optional.isPresent()) {
            userDao.update(user.getId(), user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
            return user;
        } else throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
    }

    public List<User> getAllUsers() {
        return userDao.findAllUsers();
    }

    public List<User> getFriends(Long id) {
        Optional<User> optional = userDao.findUser(id);
        if (optional.isPresent()) {
            return totalUserFriendsDao.findFriendsByUser(id);
        } else throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
    }

    public List<User> getUsersToLikeFilm(Long filmId) {
        return totalFilmLikeDao.findUserToLikeFilm(filmId);
    }

    public List<User> getFriendsCommon(Long userId, Long friendId) {
        return totalUserFriendsDao.findFriendsCommon(userId, friendId);
    }

    public List<Film> getRecommendation(Long userId) {
        return totalFilmLikeDao.getRecommendationForUser(userId);
    }

    public void removeUser(Long userId) {
        Optional<User> optionalUser = userDao.findUser(userId);
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        userDao.delete(userId);
    }

    public void addFriend(Long friendId, Long userId) {
        Optional<User> optionalUser = userDao.findUser(userId);
        Optional<User> optionalFriend = userDao.findUser(friendId);
        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            Optional<TotalUserFriends> optionalRowStatusUser = totalUserFriendsDao.findTotalUserFriend(userId, friendId);
            if (optionalRowStatusUser.isPresent()) {
                TotalUserFriends userStatus = optionalRowStatusUser.get();
                if (userStatus.getStatusFriend() == StatusFriend.UNCONFIRMED)
                    totalUserFriendsDao.update(userId, friendId, StatusFriend.CONFIRMED);
                eventsDao.insert(EventType.FRIEND, EventOperation.ADD, userId, friendId);
            } else totalUserFriendsDao.insert(userId, friendId, StatusFriend.CONFIRMED);
            Optional<TotalUserFriends> optionalRowStatusFriend = totalUserFriendsDao.findTotalUserFriend(friendId, userId);
            eventsDao.insert(EventType.FRIEND, EventOperation.ADD, userId, friendId);
            if (optionalRowStatusFriend.isEmpty())
                totalUserFriendsDao.insert(friendId, userId, StatusFriend.UNCONFIRMED);
        } else throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
    }

    public void removeFriend(Long userId, Long friendId) {
        totalUserFriendsDao.delete(userId, friendId);
        totalUserFriendsDao.update(friendId, userId, StatusFriend.UNCONFIRMED);
        eventsDao.insert(EventType.FRIEND, EventOperation.REMOVE, userId, friendId);
    }

    public void clearStorage() {
        userDao.delete();
    }
}
