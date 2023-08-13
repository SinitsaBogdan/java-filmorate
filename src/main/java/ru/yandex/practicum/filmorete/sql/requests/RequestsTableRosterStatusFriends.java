package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableRosterStatusFriends {

    SELECT_TABLE_ROSTER_STATUS_FRIENDS__LAST_ID(
            "SELECT MAX(ID) AS LAST_ID FROM ROSTER_STATUS_FRIENDS;"
    ),

    SELECT_TABLE_ROSTER_STATUS_FRIENDS__ALL_ROWS(
            "SELECT * FROM ROSTER_STATUS_FRIENDS;"
    ),

    SELECT_TABLE_ROSTER_STATUS_FRIENDS__ROW_BY_ID(
            "SELECT * FROM ROSTER_STATUS_FRIENDS " +
                    "WHERE ID = ?;"
    ),

    SELECT_TABLE_ROSTER_STATUS_FRIENDS__ROW_BY_NAME(
            "SELECT * FROM ROSTER_STATUS_FRIENDS " +
                    "WHERE NAME = ?;"
    ),

    SELECT_TABLE_ROSTER_STATUS_FRIENDS__ALL_NAME(
            "SELECT NAME FROM ROSTER_STATUS_FRIENDS;"
    ),

    INSERT_TABLE_ROSTER_STATUS_FRIENDS(
            "INSERT INTO ROSTER_STATUS_FRIENDS (NAME) " +
                    "VALUES(?);"
    ),

    INSERT_TABLE_ROSTER_STATUS_FRIENDS__ALL_COLUMN(
            "INSERT INTO ROSTER_STATUS_FRIENDS (ID, NAME) " +
                    "VALUES(?, ?);"
    ),

    UPDATE_TABLE_ROSTER_STATUS_FRIENDS__ROW_BY_ID(
            "UPDATE ROSTER_STATUS_FRIENDS " +
                    "SET NAME = ? " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ROSTER_STATUS_FRIENDS__ALL_ROWS(
            "DELETE FROM ROSTER_STATUS_FRIENDS;"
    ),

    DELETE_TABLE_ROSTER_STATUS_FRIENDS__ROW_BY_ID(
            "DELETE FROM ROSTER_STATUS_FRIENDS " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ROSTER_STATUS_FRIENDS__ROW_BY_NAME(
            "DELETE FROM ROSTER_STATUS_FRIENDS " +
                    "WHERE NAME = ?;"
    );

    @Getter
    private final String template;

    RequestsTableRosterStatusFriends(String template) {
        this.template = template;
    }
}
