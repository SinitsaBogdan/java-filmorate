package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableRosterGenre {

    SELECT_TABLE_ROSTER_GENRE__LAST_ID(
            "SELECT MAX(ID) AS LAST_ID FROM ROSTER_GENRE;"
    ),

    SELECT_TABLE_ROSTER_GENRE__ALL_NAME(
            "SELECT NAME FROM ROSTER_GENRE;"
    ),

    SELECT_TABLE_ROSTER_GENRE__ALL_ROWS(
            "SELECT * FROM ROSTER_GENRE " +
                    "ORDER BY ID ASC;"
    ),

    SELECT_TABLE_ROSTER_GENRE__ROW_BY_ID(
            "SELECT * FROM ROSTER_GENRE " +
                    "WHERE ID = ?;"
    ),

    SELECT_TABLE_ROSTER_GENRE__ROW_BY_NAME(
            "SELECT * FROM ROSTER_GENRE " +
                    "WHERE NAME = ?;"
    ),

    INSERT_TABLE_ROSTER_GENRE(
            "INSERT INTO ROSTER_GENRE (NAME) " +
                    "VALUES(?);"
    ),

    INSERT_TABLE_ROSTER_GENRE__ALL_COLUMN(
            "INSERT INTO ROSTER_GENRE (ID, NAME) " +
                    "VALUES(?, ?);"
    ),

    UPDATE_TABLE_ROSTER_GENRE__ROW_BY_ID(
            "UPDATE ROSTER_GENRE " +
                    "SET " +
                    "   NAME = ? " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ROSTER_GENRE__ALL_ROWS(
            "DELETE FROM ROSTER_GENRE;"
    ),

    DELETE_TABLE_ROSTER_GENRE__ROW_BY_ID(
            "DELETE FROM ROSTER_GENRE " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ROSTER_GENRE__ROW_BY_NAME(
            "DELETE FROM ROSTER_GENRE " +
                    "WHERE NAME = ?;"
    );

    @Getter
    private final String template;

    RequestsTableRosterGenre(String template) {
        this.template = template;
    }
}
