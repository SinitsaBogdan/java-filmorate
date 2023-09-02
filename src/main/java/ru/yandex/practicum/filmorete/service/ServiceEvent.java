package ru.yandex.practicum.filmorete.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceEvent {
    private final EventsDao eventsDao;
    private final UserDao userDao;

    public List<Event> getAllEventByUserId(Long userId) {
        Optional<User> user = userDao.findUser(userId);
        if (user.isEmpty()) {
            throw new ExceptionNotFoundUserStorage(MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        }
        return eventsDao.findAllByUserId(userId);
    }
}
