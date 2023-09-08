package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum DirectorRequests {

    SELECT_ALL__DIRECTOR(
        "SELECT * FROM DIRECTORS;"
    ),

    SELECT_ONE__DIRECTOR__ID(
        "SELECT * FROM DIRECTORS " +
            "WHERE id = ?;"
    ),

    SELECT_ONE__DIRECTOR__NAME(
        "SELECT * FROM DIRECTORS " +
            "WHERE name = ?;"
    ),

    SELECT_MAX_ID__DIRECTOR(
        "SELECT MAX(id) FROM DIRECTORS;"
    ),

    INSERT_ONE__DIRECTOR__NAME(
        "INSERT INTO DIRECTORS (name) " +
            "VALUES(?);"
    ),

    INSERT_ONE__DIRECTOR__FULL(
        "INSERT INTO DIRECTORS (id, name) " +
            "VALUES(?, ?);"
    ),

    UPDATE_ONE__DIRECTOR__SET_NAME__ID(
        "UPDATE DIRECTORS " +
            "SET name = ? " +
            "WHERE id = ?;"
    ),

    DELETE_ALL__DIRECTOR(
        "DELETE FROM DIRECTORS;"
    ),

    DELETE_ONE__DIRECTOR__ID(
        "DELETE FROM DIRECTORS " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__DIRECTOR__NAME(
        "DELETE FROM DIRECTORS " +
            "WHERE name = ?;"
    );

    private final String sql;

    DirectorRequests(String sql) {
        this.sql = sql;
    }
}