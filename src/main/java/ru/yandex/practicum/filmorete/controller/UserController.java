package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ExceptionValidationUser;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping()
    public Collection<User> findAll() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User findToId(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getFriendsCommon(@PathVariable Long id, @PathVariable Long otherId) {
        return service.getFriendsCommon(id, otherId);
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) throws ExceptionValidationUser {
        return service.createUser(user);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) throws ExceptionValidationUser {
        return service.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriends(@PathVariable Long friendId, @PathVariable Long userId) {
        service.addFriend(friendId, userId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId) {
        service.removeFriend(id, friendId);
    }

    @DeleteMapping()
    public void clear() {
        service.clearStorage();
    }
}
