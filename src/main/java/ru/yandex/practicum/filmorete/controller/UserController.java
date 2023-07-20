package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ValidationUserException;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.service.UserService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Collection<User> findAll() {
        return userService.storage.getUser();
    }

    @GetMapping("/{id}")
    public User findToId(@PathVariable Long id) {

        if (userService.storage.getCollectionsIdUsers().contains(id)) {
            return userService.storage.getUser(id);
        } else {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        return userService.storage.getUser(id).getFriends().stream()
                .map(userService.storage::getUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getFriendsCommon(@PathVariable Long id, @PathVariable Long otherId) {
        if (userService.storage.getCollectionsIdUsers().contains(id) && userService.storage.getCollectionsIdUsers().contains(otherId)) {
            return userService.getSharedFriends(userService.storage.getUser(id), userService.storage.getUser(otherId));
        } else {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) throws ValidationUserException {
        if (!userService.storage.getEmails().contains(user.getEmail())) {
            validatorUser(user);
            userService.storage.addUser(user);
            log.info("Добавлен новый пользователь!");
            return user;
        } else {
            throw new ValidationUserException(VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS);
        }
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) throws ValidationUserException {

        if (user.getId() == null) {
            throw new ValidationUserException(VALID_ERROR_USER_NOT_ID);
        }
        if (!userService.storage.getCollectionsIdUsers().contains(user.getId())) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
        validatorUser(user);
        userService.storage.updateUser(user);
        log.info("Пользователь {} успешно обновлен!", user.getName());
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable Long friendId, @PathVariable Long id) {

        if (
                !userService.storage.getCollectionsIdUsers().contains(id) ||
                !userService.storage.getCollectionsIdUsers().contains(friendId)
        ) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }

        userService.addFriend(userService.storage.getUser(id), userService.storage.getUser(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable Long friendId, @PathVariable Long id) {

        if (
                !userService.storage.getCollectionsIdUsers().contains(id) ||
                !userService.storage.getCollectionsIdUsers().contains(friendId)
        ) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }

        userService.removeFriend(userService.storage.getUser(id), userService.storage.getUser(friendId));
    }

    @DeleteMapping()
    public void clear() {
        userService.storage.clear();
        log.info("Очистка хранилища Пользователей!");
    }

    private void validatorUser(User user) throws ValidationUserException {
        if (user.getLogin().contains(" ")) {
            throw new ValidationUserException(VALID_ERROR_USER_LOGIN_IS_WHITESPACE);
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        if (user.getLikesFilms() == null) {
            user.setLikesFilms(new HashSet<>());
        }
    }
}
