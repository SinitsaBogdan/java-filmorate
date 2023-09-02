package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum RosterMpaRequests {
    SELECT_ALL__ROSTER_MPA__NAME(
        "SELECT name " +
            "FROM ROSTER_MPA;"
    ),

    SELECT_ALL__ROSTER_MPA__DESCRIPTION(
        "SELECT DESCRIPTION " +
            "FROM ROSTER_MPA;"
    ),

    SELECT_ALL__ROSTER_MPA(
        "SELECT * " +
            "FROM ROSTER_MPA;"
    ),

    SELECT_ONE__ROSTER_MPA__ID(
        "SELECT * FROM ROSTER_MPA " +
            "WHERE id = ?;"
    ),

    SELECT_ONE__ROSTER_MPA__NAME(
        "SELECT * FROM ROSTER_MPA " +
            "WHERE name = ?;"
    ),

    INSERT_ONE__ROSTER_MPA_FULL(
        "INSERT INTO ROSTER_MPA (name, description) " +
            "VALUES(?, ?);"
    ),

    INSERT_ONE__ROSTER_MPA_FULL__ID(
        "INSERT INTO ROSTER_MPA (id, name, description) " +
            "VALUES(?, ?, ?);"
    ),

    UPDATE_ONE__ROSTER_MPA__SET_NAME_DESCRIPTION__ID(
        "UPDATE ROSTER_MPA " +
            "SET " +
            "name = ?, " +
            "description = ? " +
            "WHERE id = ?;"
    ),

    UPDATE_ONE__ROSTER_MPA__SET_NAME_DESCRIPTION__NAME(
        "UPDATE ROSTER_MPA " +
            "SET " +
            "name = ?, " +
            "description = ? " +
            "WHERE name = ?;"
    ),

    DELETE_ALL__ROSTER_MPA(
        "DELETE FROM ROSTER_MPA;"
    ),

    DELETE_ONE__ROSTER_MPA__ID(
        "DELETE FROM ROSTER_MPA " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__ROSTER_MPA__NAME(
        "DELETE FROM ROSTER_MPA " +
            "WHERE name = ?;"
    );

    private final String sql;

    RosterMpaRequests(String sql) {
        this.sql = sql;
    }
}
