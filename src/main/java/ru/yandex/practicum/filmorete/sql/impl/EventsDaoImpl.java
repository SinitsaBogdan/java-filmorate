package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.sql.requests.EventsRequests.*;

@Component
@RequiredArgsConstructor
public class EventsDaoImpl implements EventsDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> findAll() {
        List<Event> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__EVENTS.getSql());
        while (row.next()) result.add(FactoryModel.buildEvent(row));
        return result;
    }

    @Override
    public List<Event> findAll(Long userId) {
        List<Event> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__EVENTS__USER.getSql(), userId);
        while (row.next()) result.add(FactoryModel.buildEvent(row));
        return result;
    }

    @Override
    public Optional<Event> findOne(Long eventId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__EVENTS__ID.getSql(), eventId);
        if (row.next()) return Optional.of(FactoryModel.buildEvent(row));
        else return Optional.empty();
    }

    @Override
    public List<Event> findAll(EventType eventType, Long entityId) {
        List<Event> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__EVENTS__ENTITY_TYPE.getSql(),
                entityId, eventType.name()
        );
        while (row.next()) result.add(FactoryModel.buildEvent(row));
        return result;
    }

    @Override
    public Optional<Event> findOne(EventType eventType, Long entityId, Long userId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__EVENTS__TYPE_ENTITY_USER.getSql(),
                entityId, eventType.name(), userId
        );
        if (row.next()) return Optional.of(FactoryModel.buildEvent(row));
        else return Optional.empty();
    }

    @Override
    public void insert(EventType eventType, EventOperation operation, Long userId, Long entityId) {
        jdbcTemplate.update(
                INSERT_ONE__EVENTS_FULL__USER_TYPE_OPERATION_ENTITY_TIMESTAMP.getSql(),
                userId, eventType.name(), operation.name(), entityId, LocalDateTime.now()
        );
    }

    @Override
    public void insert(Long id, EventType type, EventOperation operation, Long userId, Long entityId) {
        jdbcTemplate.update(
                INSERT_ONE__EVENTS_FULL.getSql(),
                id, userId, type.name(), operation.name(), entityId, LocalDateTime.now()
        );
    }

    @Override
    public void update(Long id, EventType type, EventOperation operation, Long userId, Long entityId) {
        jdbcTemplate.update(
                UPDATE_ONE__EVENTS__SET_TYPE_OPERATION_USER_ENTITY__ID.getSql(),
                type.name(), operation.name(), userId, entityId, id
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL__EVENTS.getSql());
    }

    @Override
    public void deleteAllIsEventId(Long id) {
        jdbcTemplate.update(DELETE_ONE__EVENTS__ID.getSql(), id);
    }

    @Override
    public void deleteAll(EventType eventType, Long entityId) {
        jdbcTemplate.update(DELETE_ONE__EVENTS__ENTITY_TYPE.getSql(), entityId, eventType.name());
    }

    @Override
    public void deleteAll(EventType eventType) {
        jdbcTemplate.update(DELETE_ALL__EVENTS__TYPE.getSql(), eventType.name());
    }

    @Override
    public void deleteAll(EventOperation operation) {
        jdbcTemplate.update(DELETE_ALL__EVENTS__OPERATION.getSql(), operation.name());
    }

    @Override
    public void deleteAllIsUserId(Long userId) {
        jdbcTemplate.update(DELETE_ALL__EVENTS__USER_ID.getSql(), userId);
    }
}
