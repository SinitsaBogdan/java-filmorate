package ru.yandex.practicum.filmorete.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEvent {
    private final EventsDao eventsDao;

    /**
     * NEW!!!
     * Запрос всех событий из таблицы EVENTS по USER_ID [ EVENTS ].
     */
    public List<Event> getAllEventByUserId(Long userId) {
        return eventsDao.findAllByUserId(userId);
    }
}
