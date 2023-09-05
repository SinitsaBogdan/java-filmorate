package ru.yandex.practicum.filmorete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.exeptions.FilmorateException;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.ResponseErrorMessage.*;
import static ru.yandex.practicum.filmorete.service.ServiceValidators.checkValidUser;

@Service
public class ServiceUser {

    private final UserDao userDao;
    private final FilmDao filmDao;

    private final TotalUserFriendsDao totalUserFriendsDao;

    private final TotalFilmLikeDao totalFilmLikeDao;

    private final EventsDao eventsDao;

    @Autowired
    private ServiceUser(UserDao userDao, FilmDao filmDao, TotalUserFriendsDao totalUserFriendsDao, TotalFilmLikeDao totalFilmLikeDao, EventsDao eventsDao) {
        this.userDao = userDao;
        this.filmDao = filmDao;
        this.totalUserFriendsDao = totalUserFriendsDao;
        this.totalFilmLikeDao = totalFilmLikeDao;
        this.eventsDao = eventsDao;
    }

    public User getUser(Long userId) {
        Optional<User> optional = userDao.findById(userId);
        if (optional.isPresent()) return optional.get();
        else throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
    }

    public User createUser(User user) {
        checkValidUser(user);
        Optional<User> optional = userDao.findByEmail(user.getEmail());
        if (optional.isPresent()) throw new FilmorateException(ERROR__USER__DOUBLE_EMAIL_IN_COLLECTIONS);
        userDao.insert(user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
        return userDao.findByEmail(user.getEmail()).get();
    }

    public User updateUser(User user) {
        checkValidUser(user);
        Optional<User> optional = userDao.findById(user.getId());
        if (optional.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        userDao.update(user.getId(), user.getName(), user.getBirthday(), user.getLogin(), user.getEmail());
        return user;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public List<User> getFriends(Long id) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        return totalUserFriendsDao.findAllByUserId(id);
    }

    public List<User> getUsersToLikeFilm(Long filmId) {
        Optional<Film> optional = filmDao.findFilmById(filmId);
        if (optional.isEmpty()) throw new FilmorateException(ERROR__FILM__ID_NOT_IN_COLLECTIONS);
        return totalFilmLikeDao.findUserToLikeFilm(filmId);
    }

    public List<User> getFriendsCommon(Long userId, Long friendId) {
        Optional<User> optionalUser = userDao.findById(userId);
        Optional<User> optionalFriend = userDao.findById(userId);
        if (optionalUser.isEmpty() || optionalFriend.isEmpty()) {
            throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        }
        else return totalUserFriendsDao.findFriendsCommon(userId, friendId);
    }

    public List<Film> getRecommendation(Long userId) {
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        return totalFilmLikeDao.findRecommendationForUser(userId);
    }

    public void removeUser(Long userId) {
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        userDao.deleteById(userId);
    }

    public void addFriend(Long friendId, Long userId) {
        Optional<User> optionalUser = userDao.findById(userId);
        Optional<User> optionalFriend = userDao.findById(friendId);
        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            Optional<TotalUserFriends> optionalRowStatusUser = totalUserFriendsDao.findByUserIdAndFriendId(userId, friendId);
            if (optionalRowStatusUser.isPresent()) {
                TotalUserFriends userStatus = optionalRowStatusUser.get();
                if (userStatus.getStatusFriend().equals(StatusFriend.UNCONFIRMED)) {
                    totalUserFriendsDao.update(userId, friendId, StatusFriend.CONFIRMED);
                }
            } else {
                totalUserFriendsDao.insert(userId, friendId, StatusFriend.CONFIRMED);
                eventsDao.insert(EventType.FRIEND, EventOperation.ADD, userId, friendId);
            }
            Optional<TotalUserFriends> optionalRowStatusFriend = totalUserFriendsDao.findByUserIdAndFriendId(friendId, userId);
            if (optionalRowStatusFriend.isEmpty()) totalUserFriendsDao.insert(friendId, userId, StatusFriend.UNCONFIRMED);
        } else throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
    }

    public void removeFriend(Long userId, Long friendId) {
        Optional<User> optionalUser = userDao.findById(userId);
        Optional<User> optionalFriend = userDao.findById(userId);
        if (optionalUser.isEmpty() || optionalFriend.isEmpty())
            throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        totalUserFriendsDao.deleteAllByUserIdAndFriendId(userId, friendId);
        totalUserFriendsDao.update(friendId, userId, StatusFriend.UNCONFIRMED);
        eventsDao.insert(EventType.FRIEND, EventOperation.REMOVE, userId, friendId);
    }

    public void clearStorage() {
        userDao.deleteAll();
    }
}
