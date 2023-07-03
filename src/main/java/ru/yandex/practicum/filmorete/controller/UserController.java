package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.exeptions.ValidationUserException;
import ru.yandex.practicum.filmorete.model.User;

import javax.validation.Valid;
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
    public User create(@Valid @RequestBody User user) throws ValidationUserException {
        if (!emails.contains(user.getEmail())) {
            validatorUser(user);
            user.setId(getLastIdentification());
            users.put(user.getId(), user);
            emails.add(user.getEmail());
            log.info("Добавлен новый пользователь!");
            return user;
        } else {
            throw new ValidationUserException(VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS, 400);
        }
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) throws ValidationUserException {

        if (user.getId() == null) {
            throw new ValidationUserException(VALID_ERROR_USER_NOT_ID, 400);
        }
        if (!users.containsKey(user.getId())) {
            throw new ValidationUserException(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS, 500);
        }
        validatorUser(user);
        users.put(user.getId(), user);
        log.info("Пользователь {} успешно обновлен!", user.getName());
        return user;
    }

    @DeleteMapping()
    public void clear() {
        users.clear();
        emails.clear();
        lastIdentification = 1;
        log.info("Очистка хранилища Пользователей!");
    }

    private void validatorUser(User user) throws ValidationUserException {
        if (user.getLogin().contains(" ")) {
            throw new ValidationUserException(VALID_ERROR_USER_LOGIN_IS_WHITESPACE, 400);
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private Integer getLastIdentification() {
        return lastIdentification++;
    }
}
