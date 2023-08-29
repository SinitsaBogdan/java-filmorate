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
    private ServiceUser(UserDao userDao, TotalUserFriendsDao totalUserFriendsDao, TotalFilmLikeDao totalFilmLikeDao) {
        this.userDao = userDao;
        this.totalUserFriendsDao = totalUserFriendsDao;
        this.totalFilmLikeDao = totalFilmLikeDao;
    }

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
                if (userStatus.getStatusId() == 1) totalUserFriendsDao.update(userId, friendId, 2);
            } else totalUserFriendsDao.insert(userId, friendId, 2);
            Optional<TotalUserFriends> optionalRowStatusFriend = totalUserFriendsDao.findTotalUserFriend(friendId, userId);
            if (optionalRowStatusFriend.isEmpty()) totalUserFriendsDao.insert(friendId, userId, 1);
        } else throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
    }

    public void removeFriend(Long userId, Long friendId) {
        totalUserFriendsDao.delete(userId, friendId);
        totalUserFriendsDao.update(friendId, userId, 1);
    }

    public void clearStorage() {
        userDao.delete();
    }
}
