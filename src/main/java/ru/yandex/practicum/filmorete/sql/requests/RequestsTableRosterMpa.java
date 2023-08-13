package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableRosterMpa {

    SELECT_TABLE_ROSTER_MPA__LAST_ID(
            "SELECT MAX(ID) AS LAST_ID FROM ROSTER_MPA;"
    ),

    SELECT_TABLE_ROSTER_MPA__ALL_ROWS(
            "SELECT * FROM ROSTER_MPA;"
    ),

    SELECT_TABLE_ROSTER_MPA__ROW_BY_ID(
            "SELECT * FROM ROSTER_MPA " +
                    "WHERE ID = ?;"
    ),

    SELECT_TABLE_ROSTER_MPA__ROW_BY_NAME(
            "SELECT * FROM ROSTER_MPA " +
                    "WHERE NAME = ?;"
    ),

    SELECT_TABLE_ROSTER_MPA__ALL_NAME(
            "SELECT NAME FROM ROSTER_MPA;"
    ),

    SELECT_TABLE_ROSTER_MPA__ALL_DESCRIPTIONS(
            "SELECT DESCRIPTION FROM ROSTER_MPA;"
    ),

    INSERT_TABLE_ROSTER_MPA(
            "INSERT INTO ROSTER_MPA (NAME, DESCRIPTION) " +
                    "VALUES(?, ?);"
    ),

    INSERT_TABLE_ROSTER_MPA__ALL_COLUMN(
            "INSERT INTO ROSTER_MPA (ID, NAME, DESCRIPTION) " +
                    "VALUES(?, ?, ?);"
    ),

    UPDATE_TABLE_ROSTER_MPA__ROW_BY_ID(
            "UPDATE ROSTER_MPA " +
                    "SET " +
                        "NAME = ?, " +
                        "DESCRIPTION = ? " +
                    "WHERE ID = ?;"
    ),

    UPDATE_TABLE_ROSTER_MPA__ROW_BY_NAME(
            "UPDATE ROSTER_MPA " +
                    "SET " +
                        "NAME = ?, " +
                        "DESCRIPTION = ? " +
                    "WHERE NAME = ?;"
    ),

    DELETE_TABLE_ROSTER_MPA__ALL_ROWS(
            "DELETE FROM ROSTER_MPA;"
    ),

    DELETE_TABLE_ROSTER_MPA__ROW_BY_ID(
            "DELETE FROM ROSTER_MPA " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ROSTER_MPA__ROW_BY_NAME(
            "DELETE FROM ROSTER_MPA " +
                    "WHERE NAME = ?;"
    );

    @Getter
    private final String template;

    RequestsTableRosterMpa(String template) {
        this.template = template;
    }
}
