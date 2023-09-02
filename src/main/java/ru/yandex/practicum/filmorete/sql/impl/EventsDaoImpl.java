package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventsDaoImpl implements EventsDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> findAll() {
        List<Event> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM EVENTS;"
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public List<Event> findAllByUserId(Long userId) {
        List<Event> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
          "SELECT * FROM EVENTS WHERE user_id = ?;",
          userId
        );
        while (rows.next()) result.add(buildModel(rows));
        return result;
    }

    @Override
    public Optional<Event> findByEventId(Long eventId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM EVENTS WHERE id = ?;",
                eventId
        );
        if (rows.next()) return Optional.of(buildModel(rows));
        else return Optional.empty();
    }

    @Override
    public Optional<Event> findByEventTypeAndEntityId(EventType eventType, Long eventId) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM EVENTS WHERE id = ? AND type = ?;",
                eventId, eventType.name()
        );
        if (rows.next()) return Optional.of(buildModel(rows));
        else return Optional.empty();
    }

    @Override
    public void insert(EventType eventType, EventOperation operation, Long userId, Long entityId) {
        jdbcTemplate.update(
                "INSERT INTO EVENTS (user_id, type, operation, entity_id, timestamp) " +
                        "VALUES (?, ?, ?, ?, ?);",
                userId, eventType.name(), operation.name(), entityId, LocalDateTime.now()
        );
    }

    @Override
    public void insert(Long id, EventType type, EventOperation operation, Long userId, Long entityId) {
        jdbcTemplate.update(
                "INSERT INTO EVENTS (id, user_id, type, operation, entity_id, timestamp) " +
                        "VALUES (?, ?, ?, ?, ?);",
                id, userId, type.name(), operation.name(), entityId, LocalDateTime.now()
        );
    }

    @Override
    public void update(Long id, EventType type, EventOperation operation, Long userId, Long entityId) {
        jdbcTemplate.update(
                "UPDATE EVENTS " +
                        "SET " +
                        "type = ?, " +
                        "operation = ?, " +
                        "user_id = ?, " +
                        "entity_id = ? " +
                        "WHERE id = ?;",
                type.name(), operation.name(), userId, entityId, id
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM EVENTS;"
        );
    }

    @Override
    public void deleteByEventId(Long id) {
        jdbcTemplate.update(
                "DELETE FROM EVENTS WHERE id = ?;",
                id
        );
    }

    @Override
    public void deleteAll(EventType eventType) {
        jdbcTemplate.update(
                "DELETE FROM EVENTS WHERE type = ?;",
                eventType.name()
        );
    }

    @Override
    public void deleteAll(EventOperation operation) {
        jdbcTemplate.update(
                "DELETE FROM EVENTS WHERE operation = ?;",
                operation.name()
        );
    }

    @Override
    public void deleteAll(Long userId) {
        jdbcTemplate.update(
                "DELETE FROM EVENTS WHERE user_id = ?;",
                userId
        );
    }

    protected Event buildModel(@NotNull SqlRowSet row) {
        return Event.builder()
                .eventId(row.getLong("ID"))
                .timestamp(Objects.requireNonNull(row.getTimestamp("TIMESTAMP")).getTime())
                .userId(row.getLong("USER_ID"))
                .eventType(EventType.valueOf(row.getString("TYPE")))
                .operation(EventOperation.valueOf(row.getString("OPERATION")))
                .entityId(row.getLong("ENTITY_ID"))
                .build();
    }
}
