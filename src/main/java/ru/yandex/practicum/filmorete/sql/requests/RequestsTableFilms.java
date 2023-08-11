package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableFilms {

    SELECT_TABLE_FILMS__LAST_ID(
            "SELECT MAX(ID) AS LAST_ID FROM FILMS;"
    ),

    SELECT_TABLE_FILMS__ALL_ROWS(
            "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ENUM_MPA.ID AS MPA_ID, " +
                        "ENUM_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ENUM_MPA AS ENUM_MPA ON FILMS.MPA_ID = ENUM_MPA.ID;"
    ),

    SELECT_TABLE_FILMS__ROW_BY_NAME(
            "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ENUM_MPA.ID AS MPA_ID, " +
                        "ENUM_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ENUM_MPA AS ENUM_MPA ON FILMS.MPA_ID = ENUM_MPA.ID " +
                    "WHERE FILMS.NAME = ?;"
    ),

    SELECT_TABLE_FILMS__ROW_BY_ID(
            "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ENUM_MPA.ID AS MPA_ID, " +
                        "ENUM_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION " +
                        "FROM FILMS AS FILMS " +
                    "INNER JOIN ENUM_MPA AS ENUM_MPA ON FILMS.MPA_ID = ENUM_MPA.ID " +
                    "WHERE FILMS.ID = ?;"
    ),

    INSERT_TABLE_FILMS(
            "INSERT INTO FILMS (MPA_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION) " +
                    "VALUES (?, ?, ?, ?, ?);"
    ),
    INSERT_TABLE_FILMS_All_COLUMN(
            "INSERT INTO FILMS (ID, MPA_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION) " +
                    "VALUES (?, ?, ?, ?, ?, ?);"
    ),

    UPDATE_TABLE_FILMS__ROW_BY_ID(
            "UPDATE FILMS " +
                    "SET " +
                        "MPA_ID = ?, " +
                        "NAME = ?, " +
                        "DESCRIPTION = ?, " +
                        "RELEASE_DATE = ?, " +
                        "DURATION = ? " +
                    "WHERE  ID = ?;"
    ),
    UPDATE_TABLE_FILMS__ROW_BY_NAME(
            "UPDATE FILMS " +
                    "SET " +
                        "MPA_ID = ?, " +
                        "NAME = ?, " +
                        "DESCRIPTION = ?, " +
                        "RELEASE_DATE = ?, " +
                        "DURATION = ? " +
                    "WHERE  NAME = ?;"
    ),
    DELETE_TABLE_FILMS__ALL_ROWS(
            "DELETE FROM FILMS;"
    ),
    DELETE_TABLE_FILMS__ROW_BY_ID(
            "DELETE FROM FILMS WHERE ID = ?;"
    ),
    DELETE_TABLE_FILMS__ROW_BY_NAME(
            "DELETE FROM FILMS WHERE NAME = ?;"
    ),
    DELETE_TABLE_FILMS__ROW_BY_RELEASE_DATE(
            "DELETE FROM FILMS WHERE RELEASE_DATE = ?;"
    ),
    DELETE_TABLE_FILMS__ROW_BY_DURATION(
            "DELETE FROM FILMS WHERE DURATION = ?;"
    ),
    DELETE_TABLE_FILMS__ROW_BY_MPA_ID(
            "DELETE FROM FILMS WHERE MPA_ID = ?;"
    );

    @Getter
    private final String template;

    RequestsTableFilms(String template) {
        this.template = template;
    }
}
