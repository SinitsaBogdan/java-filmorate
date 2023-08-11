package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalUserFriendsDao;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.*;

@Slf4j
@Service
public class ServiceUser {

    private final UserDao userDao;
    private final TotalUserFriendsDao totalUserFriendsDao;
    private final TotalFilmLikeDao totalFilmLikeDao;

    @Autowired
    public ServiceUser(UserDao userDao, TotalUserFriendsDao totalUserFriendsDao, TotalFilmLikeDao totalFilmLikeDao) {
        this.userDao = userDao;
        this.totalUserFriendsDao = totalUserFriendsDao;
        this.totalFilmLikeDao = totalFilmLikeDao;
    }

    public User getUser(Long userId) {
        Optional<User> optional = userDao.findRow(userId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
    }

    public User createUser(User user) {
        checkValidUser(user);
        Optional<User> optional = userDao.findRow(user.getEmail());
        if (optional.isEmpty()) {
            userDao.insert(user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
            return userDao.findRow(user.getEmail()).get();
        } else {
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_DOUBLE_EMAIL_IN_COLLECTIONS);
        }
    }

    public User updateUser(User user) {
        checkValidUser(user);
        Optional<User> optional = userDao.findRow(user.getId());
        if (optional.isPresent()) {
            userDao.update(user.getId(), user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
            return user;
        } else {
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
    }

    public void removeUser(Long id) {
        userDao.delete(id);
    }

    public List<User> getAllUsers() {
        Optional<List<User>> optional = userDao.findRows();
        return optional.orElse(null);
    }

    public List<User> getUsersToLikeFilm(Long filmId) {
        Optional<List<User>> optional = totalFilmLikeDao.findUserToLikeFilm(filmId);
        return optional.orElse(null);
    }

    public void addFriend(Long friendId, Long userId) {
        Optional<User> optionalUser = userDao.findRow(userId);
        Optional<User> optionalFriend = userDao.findRow(friendId);

        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            Optional<TotalUserFriends> optionalRowStatusUser = totalUserFriendsDao.findRow(userId, friendId);
            if (optionalRowStatusUser.isPresent()) {
                TotalUserFriends userStatus = optionalRowStatusUser.get();
                if (userStatus.getStatusId() == 1) {
                    totalUserFriendsDao.update(userId, friendId, 2);
                }
            } else {
                totalUserFriendsDao.insert(userId, friendId, 2);
            }
            Optional<TotalUserFriends> optionalRowStatusFriend = totalUserFriendsDao.findRow(friendId, userId);
            if (optionalRowStatusFriend.isEmpty()) {
                totalUserFriendsDao.insert(friendId, userId, 1);
            }
        } else {
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
    }

    public List<User> getFriends(Long id) {
        Optional<List<User>> optional = totalUserFriendsDao.findFriendsByUser(id);
        return optional.orElse(null);
    }

    public void removeFriend(Long userId, Long friendId) {
        totalUserFriendsDao.delete(userId, friendId);
        totalUserFriendsDao.update(friendId, userId, 1);
    }

    public List<User> getFriendsCommon(Long userId, Long friendId) {
        Optional<List<User>> optional = totalUserFriendsDao.findFriendsCommon(userId, friendId);
        return optional.orElse(null);
    }

    public void clearStorage() {
        userDao.delete();
    }
}
