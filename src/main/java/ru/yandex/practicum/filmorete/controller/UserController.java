package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ValidationUserException;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;
import java.util.*;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private Integer lastIdentification = 1;

    @GetMapping()
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping()
    public User create(@RequestBody User user) throws ValidationUserException {
        if (!emails.contains(user.getEmail())) {
            validatorUser(user);
            user.setId(getLastIdentification());
            users.put(user.getId(), user);
            emails.add(user.getEmail());
            log.debug("Добавлен новый пользователь!");
            return user;
        } else {
            throw new ValidationUserException(VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS, 400);
        }
    }

    @PutMapping()
    public User update(@RequestBody User user) throws ValidationUserException {

        if (user.getId() == null) {
            throw new ValidationUserException(VALID_ERROR_USER_NOT_ID, 400);
        }
        if (user.getId() < 1) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_CORRECT, 400);
        }
        if (!users.containsKey(user.getId())) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS, 500);
        }
        validatorUser(user);
        users.put(user.getId(), user);
        log.debug("Пользователь {} успешно обновлен!", user.getName());
        return user;
    }

    @DeleteMapping()
    public void clear() {
        users.clear();
        emails.clear();
        lastIdentification = 1;
    }

    private void validatorUser(User user) throws ValidationUserException {
        if (user.getBirthday() == null || user.getBirthday().toString().isEmpty()) {
            throw new ValidationUserException(VALID_ERROR_USER_NOT_BIRTHDAY, 400);
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationUserException(VALID_ERROR_USER_BIRTHDAY_MAX, 400);
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationUserException(VALID_ERROR_USER_NOT_LOGIN, 400);
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationUserException(VALID_ERROR_USER_LOGIN_IS_WHITESPACE, 400);
        }
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new ValidationUserException(VALID_ERROR_USER_NOT_EMAIL, 400);
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationUserException(VALID_ERROR_USER_EMAIL_NOT_CORRECT, 400);
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private Integer getLastIdentification() {
        return lastIdentification++;
    }
}
