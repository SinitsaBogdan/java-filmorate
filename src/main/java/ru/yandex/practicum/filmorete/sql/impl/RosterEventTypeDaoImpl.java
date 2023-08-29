package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.EventType;
import ru.yandex.practicum.filmorete.sql.dao.RosterEventTypeDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RosterEventTypeDaoImpl implements RosterEventTypeDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EventType> findAll() {
        Map<Long, EventType> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "rt.id AS id, " +
                        "rt.name AS name, " +
                        "FROM ROSTER_EVENT_TYPE AS rt " +
                        "ORDER BY rt.id;"
        );
        while (rows.next()) {
            Long eventTypeId = rows.getLong("ID");
            if (!result.containsKey(eventTypeId)) {
                EventType eventType = buildModel(rows);
                result.put(eventTypeId, eventType);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<EventType> findById(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_EVENT_TYPE WHERE id = ?;",
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void insert(EventType eventType) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_EVENT_TYPE (id, name ) " +
                        "VALUES (?, ?);",
                eventType.getId(), eventType.getName()
        );
    }

    @Override
    public void update(EventType eventType) {
        jdbcTemplate.update(
                "UPDATE ROSTER_EVENT_TYPE SET name = ?  WHERE id = ?;",
                eventType.getId(), eventType.getName()
        );
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_EVENT_TYPE WHERE id = ?;",
                rowId
        );
    }

    protected EventType buildModel(@NotNull SqlRowSet row) {
        return EventType.builder()
                .id(row.getLong("ID"))
                .name(row.getString("NAME"))
                .build();
    }
}
