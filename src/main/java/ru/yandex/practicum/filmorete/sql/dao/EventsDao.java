package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventsDao {

    List<Event> findAll();

    List<Event> findAll(Long userId);

    List<Event> findAll(EventType eventType, Long entityId);

    Optional<Event> findOne(EventType eventType, Long entityId, Long userId);

    Optional<Event> findOne(Long eventId);

    void insert(EventType eventType, EventOperation operation, Long userId, Long entityId);

    void insert(Long id, EventType eventType, EventOperation operation, Long userId, Long entityId);

    void update(Long id, EventType eventType, EventOperation operation, Long userId, Long entityId);

    void deleteAll();

    void deleteAll(EventType eventType);

    void deleteAll(EventType eventType, Long entityId);

    void deleteAll(EventOperation operation);

    void deleteAllIsEventId(Long eventId);

    void deleteAllIsUserId(Long userId);
}