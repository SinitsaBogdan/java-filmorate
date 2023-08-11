package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableEnumMpa {

    SELECT_TABLE_ENUM_MPA__LAST_ID(
            "SELECT MAX(ID) AS LAST_ID FROM ENUM_MPA;"
    ),

    SELECT_TABLE_ENUM_MPA__ALL_ROWS(
            "SELECT * FROM ENUM_MPA;"
    ),
    SELECT_TABLE_ENUM_MPA__ROW_BY_ID(
            "SELECT * FROM ENUM_MPA " +
                    "WHERE ID = ?;"
    ),
    SELECT_TABLE_ENUM_MPA__ROW_BY_NAME(
            "SELECT * FROM ENUM_MPA " +
                    "WHERE NAME = ?;"
    ),
    SELECT_TABLE_ENUM_MPA__ALL_NAME(
            "SELECT NAME FROM ENUM_MPA;"
    ),
    SELECT_TABLE_ENUM_MPA__ALL_DESCRIPTIONS(
            "SELECT DESCRIPTION FROM ENUM_MPA;"
    ),
    INSERT_TABLE_ENUM_MPA(
            "INSERT INTO ENUM_MPA (NAME, DESCRIPTION) " +
                    "VALUES(?, ?);"
    ),
    INSERT_TABLE_ENUM_MPA__ALL_COLUMN(
            "INSERT INTO ENUM_MPA (ID, NAME, DESCRIPTION) " +
                    "VALUES(?, ?, ?);"
    ),
    UPDATE_TABLE_ENUM_MPA__ROW_BY_ID(
            "UPDATE ENUM_MPA " +
                    "SET " +
                        "NAME = ?, " +
                        "DESCRIPTION = ? " +
                    "WHERE ID = ?;"
    ),
    UPDATE_TABLE_ENUM_MPA__ROW_BY_NAME(
            "UPDATE ENUM_MPA " +
                    "SET " +
                        "NAME = ?, " +
                        "DESCRIPTION = ? " +
                    "WHERE NAME = ?;"
    ),
    DELETE_TABLE_ENUM_MPA__ALL_ROWS(
            "DELETE FROM ENUM_MPA;"
    ),
    DELETE_TABLE_ENUM_MPA__ROW_BY_ID(
            "DELETE FROM ENUM_MPA " +
                    "WHERE ID = ?;"
    ),
    DELETE_TABLE_ENUM_MPA__ROW_BY_NAME(
            "DELETE FROM ENUM_MPA " +
                    "WHERE NAME = ?;"
    );

    @Getter
    private final String template;

    RequestsTableEnumMpa(String template) {
        this.template = template;
    }
}
