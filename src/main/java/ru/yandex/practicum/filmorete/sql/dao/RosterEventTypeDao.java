package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.EventType;

import java.util.List;
import java.util.Optional;

public interface RosterEventTypeDao {

    List<EventType> findAll();

    Optional<EventType> findById(Long rowId);

    void insert(EventType eventType);

    void update(EventType eventType);

    void delete(Long rowId);

    void delete(String name);
}
