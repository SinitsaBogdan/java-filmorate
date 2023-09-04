package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventsDao {

    List<Event> findAll();

    List<Event> findById(Long userId);

    List<Event> findAllByEventTypeAndEntityId(EventType eventType, Long entityId);

    Optional<Event> findOneByEventTypeAndEntityIdAndUserId(EventType eventType, Long entityId, Long userId);

    Optional<Event> findOneByEventId(Long eventId);

    void insert(EventType eventType, EventOperation operation, Long userId, Long entityId);

    void insert(Long id, EventType eventType, EventOperation operation, Long userId, Long entityId);

    void update(Long id, EventType eventType, EventOperation operation, Long userId, Long entityId);

    void deleteAll();

    void deleteByEventType(EventType eventType);

    void deleteByEventTypeAndEntityId(EventType eventType, Long entityId);

    void deleteAllByEventOperation(EventOperation operation);

    void deleteAllIsEventId(Long eventId);

    void deleteAllIsUserId(Long userId);
}