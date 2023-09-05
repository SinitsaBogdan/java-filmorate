package ru.yandex.practicum.filmorete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.FilmorateException;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.ResponseErrorMessage.ERROR__USER__ID_NOT_IN_COLLECTIONS;

@Service
@RequiredArgsConstructor
public class ServiceEvent {
    private final EventsDao eventsDao;
    private final UserDao userDao;

    public List<Event> getAllEventByUserId(Long userId) {
        Optional<User> user = userDao.findById(userId);
        if (user.isEmpty()) throw new FilmorateException(ERROR__USER__ID_NOT_IN_COLLECTIONS);
        return eventsDao.findById(userId);
    }
}
