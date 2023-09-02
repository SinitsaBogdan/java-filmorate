package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum EventsRequests {
    SELECT_ALL__EVENTS(
        "SELECT * FROM EVENTS;"
    ),

    SELECT_ALL__EVENTS__USER_ID(
        "SELECT * FROM EVENTS " +
            "WHERE user_id = ?;"
    ),

    SELECT_ALL__EVENTS__ID(
        "SELECT * FROM EVENTS " +
            "WHERE id = ?;"
    ),

    SELECT_ALL__EVENTS__TYPE_ENTITY_ID(
        "SELECT * FROM EVENTS " +
            "WHERE entity_id = ? AND type = ?;"
    ),

    SELECT_ALL__EVENTS__TYPE__ENTITY_ID__USER_ID(
        "SELECT * FROM EVENTS " +
            "WHERE entity_id = ? AND type = ? AND user_id = ?;"
    ),

    INSERT_ONE__EVENTS_FULL(
        "INSERT INTO EVENTS (user_id, type, operation, entity_id, timestamp) " +
        "VALUES (?, ?, ?, ?, ?);"
    ),

    INSERT_ONE__EVENTS_FULL__ID(
        "INSERT INTO EVENTS (id, user_id, type, operation, entity_id, timestamp) " +
            "VALUES (?, ?, ?, ?, ?, ?);"
    ),

    UPDATE_ONE__EVENTS__SET_TYPE_OPERATION_USER_ID_ENTITY__ID(
        "UPDATE EVENTS " +
            "SET " +
            "type = ?, " +
            "operation = ?, " +
            "user_id = ?, " +
            "entity_id = ? " +
            "WHERE id = ?;"
    ),

    DELETE_ALL__EVENTS(
        "DELETE FROM EVENTS;"
    ),

    DELETE_ONE__EVENTS__ID(
        "DELETE FROM EVENTS " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__EVENTS__ENTITY_ID_TYPE(
        "DELETE FROM EVENTS " +
            "WHERE entity_id = ? AND type = ?;"
    ),

    DELETE_ALL__EVENTS__TYPE(
        "DELETE FROM EVENTS " +
            "WHERE type = ?;"
    ),

    DELETE_ALL__EVENTS__OPERATION(
        "DELETE FROM EVENTS " +
            "WHERE operation = ?;"
    ),

    DELETE_ALL__EVENTS__USER_ID(
        "DELETE FROM EVENTS " +
            "WHERE user_id = ?;"
    );

    private final String sql;

    EventsRequests(String sql) {
        this.sql = sql;
    }
}
