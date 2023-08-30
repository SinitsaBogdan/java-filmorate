package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventsDaoImpl implements EventsDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> findAll() {

        Map<Long, Event> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "e.id AS id, " +
                        "e.user_id AS userId, " +
                        "e.type_id AS typeId, " +
                        "e.timestamp AS releaseDate, " +
                        "e.entity_id AS entityId, " +
                        "re.name AS typeName " +
                        "FROM EVENTS AS e " +
                        "RIGHT JOIN ROSTER_EVENT_TYPE AS re ON e.type_id = re.id " +
                        "ORDER BY e.id;"
        );
        while (rows.next()) {
            Long eventId = rows.getLong("ID");
            if (!result.containsKey(eventId)) {
                Event event = buildModel(rows);
                result.put(eventId, event);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<Event> findById(Long rowId) {
        Map<Long, Event> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "*, " +
                        "re.name AS TYPE_NAME " +
                        "FROM EVENTS AS e " +
                        "RIGHT JOIN ROSTER_EVENT_TYPE AS re ON e.type_id = re.id " +
                        "ORDER BY e.id;",
                rowId
        );
        while (rows.next()) {
            Long eventId = rows.getLong("ID");
            if (!result.containsKey(eventId)) {
                Event event = buildModel(rows);
                result.put(rowId, event);
            }
        }
        return Optional.ofNullable(result.get(rowId));
    }

    @Override
    public void insert(Event event) {
        jdbcTemplate.update(
                "INSERT INTO EVENTS (id, user_id, type_id, timestamp, entity_id) " +
                        "VALUES (?, ?, ?, ?, ?);",
                event.getId(), event.getUserId(), event.getTypeId(), event.getReleaseDate(), event.getEntityId()
        );
    }

    @Override
    public void update(Event event) {
        jdbcTemplate.update(
                "UPDATE EVENTS " +
                        "SET " +
                        "id = ?, " +
                        "user_id = ?, " +
                        "type_id = ?, " +
                        "timestamp = ?, " +
                        "entity_id = ?, " +
                        "WHERE id = ?;",
                event.getId(), event.getUserId(), event.getTypeId(), event.getReleaseDate(), event.getEntityId()
        );
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM EVENTS WHERE id = ?;",
                rowId
        );
    }

    @Override
    public List<Event> findAllByUserId(Long userId) {
        Map<Long, Event> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "*, " +
                        "re.name AS TYPE_NAME " +
                        "FROM EVENTS AS e " +
                        "RIGHT JOIN ROSTER_EVENT_TYPE AS re ON e.type_id = re.id;");
        while (rows.next()) {
            Long eventId = rows.getLong("ID");
            if (!result.containsKey(eventId)) {
                Event event = buildModel(rows);
                result.put(eventId, event);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    protected Event buildModel(@NotNull SqlRowSet row) {
        return Event.builder()
                .id(row.getLong("ID"))
                .userId(row.getLong("USER_ID"))
                .typeId(row.getLong("TYPE_ID"))
                .timestamp(Objects.requireNonNull(row.getDate("TIMESTAMP")).toInstant().getEpochSecond())
                .entityId(row.getLong("ENTITY_ID"))
                .operation(Objects.requireNonNull(row.getString("TYPE_NAME")).split(" ")[0])
                .eventType(Objects.requireNonNull(row.getString("TYPE_NAME")).split(" ")[1])
                .build();
    }
}
