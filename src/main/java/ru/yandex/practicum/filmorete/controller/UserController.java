package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidationUser;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.service.ServiceEvent;
import ru.yandex.practicum.filmorete.service.ServiceFilm;
import ru.yandex.practicum.filmorete.service.ServiceUser;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    private final ServiceUser serviceUser;

    private final ServiceFilm serviceFilm;

    private final ServiceEvent serviceEvent;

    public UserController(ServiceUser serviceUser, ServiceFilm serviceFilm, ServiceEvent serviceEvent) {
        this.serviceUser = serviceUser;
        this.serviceFilm = serviceFilm;
        this.serviceEvent = serviceEvent;
    }

    /**
     * Запрос всех пользователей.
     */
    @GetMapping
    public List<User> findAll() {
        return serviceUser.getAllUsers();
    }

    /**
     * Добавление нового пользователя.
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) throws ExceptionValidationUser {
        return serviceUser.createUser(user);
    }

    /**
     * Обновление пользователя по id.
     */
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ExceptionValidationUser {
        return serviceUser.updateUser(user);
    }

    /**
     * Удаление всех пользователей.
     */
    @DeleteMapping
    public void clear() {
        serviceUser.clearStorage();
    }

    /**
     * Запрос пользователя по id.
     */
    @GetMapping("/{userId}")
    public User findToId(@PathVariable Long userId) {
        return serviceUser.getUser(userId);
    }

    /**
     * Удаление пользователя id.
     */
    @DeleteMapping("/{userId}")
    public void removeById(@PathVariable Long userId) {
        serviceUser.removeUser(userId);
    }

    /**
     * Возвращает ленту событий пользователя.
     */
    @GetMapping("{id}/feed")
    public List<Event> getEvents(@PathVariable("id") Long id) {
        return serviceEvent.getAllEventByUserId(id);
    }

    /**
     * Запрос списка фильмов которые лайкнул пользователь.
     */
    @GetMapping("/{userId}/to-like")
    public List<Film> getUsersToLikeFilm(@PathVariable Long userId) {
        return serviceFilm.getFilmsToLikeUser(userId);
    }

    /**
     * Возвращает рекомендации по фильмам для просмотра.
     */
    @GetMapping("/{userId}/recommendations")
    public List<Film> getRecommendedFilms(@PathVariable Long userId) {
        return serviceUser.getRecommendation(userId);
    }

    /**
     * Запрос списка друзей пользователя по id.
     */
    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable Long userId) {
        return serviceUser.getFriends(userId);
    }

    /**
     * Добавление пользователя в друзья по id.
     */
    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriends(@PathVariable Long friendId, @PathVariable Long userId) {
        serviceUser.addFriend(friendId, userId);
    }

    /**
     * Удаление пользователя из друзей по id.
     */
    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        serviceUser.removeFriend(userId, friendId);
    }

    /**
     * Запрос общих друзей по двум id.
     */
    @GetMapping("/{userId}/friends/common/{friendId}")
    public List<User> getFriendsCommon(@PathVariable Long userId, @PathVariable Long friendId) {
        return serviceUser.getFriendsCommon(userId, friendId);
    }
}