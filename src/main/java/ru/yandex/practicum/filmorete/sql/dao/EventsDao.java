package ru.yandex.practicum.filmorete.sql.dao;

import ru.yandex.practicum.filmorete.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventsDao {

    List<Event> findAll();

    Optional<Event> findById(Long rowId);

    void insert(Event event);

    void update(Event event);

    void deleteById(Long rowId);
}
