package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum RosterGenreRequests {
    SELECT_ALL__ROSTER_GENRE__COLUMN_NAME(
        "SELECT name FROM ROSTER_GENRE;"
    ),

    SELECT_ALL__ROSTER_GENRE__NAME(
        "SELECT * FROM ROSTER_GENRE " +
            "ORDER BY id ASC;"
    ),

    SELECT_ONE__ROSTER_GENRE(
        "SELECT * FROM ROSTER_GENRE " +
            "WHERE id = ?;"
    ),

    SELECT_ONE__ROSTER_GENRE__NAME(
        "SELECT * FROM ROSTER_GENRE " +
            "WHERE name = ?;"
    ),

    INSERT_ONE__ROSTER_GENRE__NAME(
        "INSERT INTO ROSTER_GENRE (name) " +
            "VALUES(?);"
    ),

    INSERT_ONE__ROSTER_GENRE__FULL(
        "INSERT INTO ROSTER_GENRE (id, name) " +
            "VALUES(?, ?);"
    ),

    UPDATE_ONE__ROSTER_GENRE__SET_NAME__ID(
        "UPDATE ROSTER_GENRE " +
            "SET name = ? " +
            "WHERE id = ?;"
    ),

    DELETE_ALL__ROSTER_GENRE(
        "DELETE FROM ROSTER_GENRE;"
    ),

    DELETE_ONE__ROSTER_GENRE__ID(
        "DELETE FROM ROSTER_GENRE " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__ROSTER_GENRE__NAME(
        "DELETE FROM ROSTER_GENRE " +
            "WHERE name = ?;"
    );

    private final String sql;

    RosterGenreRequests(String sql) {
        this.sql = sql;
    }
}
