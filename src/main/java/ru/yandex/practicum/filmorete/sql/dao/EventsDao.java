package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventsDao {

    List<Event> findAll();

    List<Event> findAllByUserId(Long userId);

    Optional<Event> findByEventId(Long eventId);

    void insert(EventType eventType, EventOperation operation, Long userId, Long entityId);

    void update(Long id, EventType eventType, EventOperation operation, Long userId, Long entityId);

    void delete();

    void deleteByEventId(Long eventId);

    void deleteAll(EventType eventType);

    void deleteAll(EventOperation operation);

    void deleteAll(Long userId);
}