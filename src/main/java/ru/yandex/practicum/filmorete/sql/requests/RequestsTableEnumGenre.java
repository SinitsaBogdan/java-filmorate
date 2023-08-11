package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableEnumGenre {

    SELECT_TABLE_ENUM_GENRE__LAST_ID(
            "SELECT MAX(ID) AS LAST_ID FROM ENUM_GENRE;"
    ),

    SELECT_TABLE_ENUM_GENRE__ALL_NAME(
            "SELECT NAME FROM ENUM_GENRE;"
    ),

    SELECT_TABLE_ENUM_GENRE__ALL_ROWS(
            "SELECT * FROM ENUM_GENRE " +
                    "ORDER BY ID ASC;"
    ),

    SELECT_TABLE_ENUM_GENRE__ROW_BY_ID(
            "SELECT * FROM ENUM_GENRE " +
                    "WHERE ID = ?;"
    ),

    SELECT_TABLE_ENUM_GENRE__ROW_BY_NAME(
            "SELECT * FROM ENUM_GENRE " +
                    "WHERE NAME = ?;"
    ),

    INSERT_TABLE_ENUM_GENRE(
            "INSERT INTO ENUM_GENRE (NAME) " +
                    "VALUES(?);"
    ),

    INSERT_TABLE_ENUM_GENRE__ALL_COLUMN(
            "INSERT INTO ENUM_GENRE (ID, NAME) " +
                    "VALUES(?, ?);"
    ),

    UPDATE_TABLE_ENUM_GENRE__ROW_BY_ID(
            "UPDATE ENUM_GENRE " +
                    "SET " +
                    "   NAME = ? " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ENUM_GENRE__ALL_ROWS(
            "DELETE FROM ENUM_GENRE;"
    ),

    DELETE_TABLE_ENUM_GENRE__ROW_BY_ID(
            "DELETE FROM ENUM_GENRE " +
                    "WHERE ID = ?;"
    ),

    DELETE_TABLE_ENUM_GENRE__ROW_BY_NAME(
            "DELETE FROM ENUM_GENRE " +
                    "WHERE NAME = ?;"
    );

    @Getter
    private final String template;

    RequestsTableEnumGenre(String template) {
        this.template = template;
    }
}
