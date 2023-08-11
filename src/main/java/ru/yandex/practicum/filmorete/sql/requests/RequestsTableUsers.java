package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableUsers {

    SELECT_TABLE_USERS__LAST_ID("SELECT MAX(ID) AS LAST_ID FROM USERS;"),

    SELECT_TABLE_USERS__ALL_ROWS("SELECT * FROM USERS;"),

    SELECT_TABLE_USERS__ROW_BY_ID("SELECT * FROM USERS WHERE ID = ?;"),

    SELECT_TABLE_USERS__ROW_BY_EMAIL("SELECT * FROM USERS WHERE EMAIL = ?;"),

    INSERT_TABLE_USERS("INSERT INTO USERS (NAME, BIRTHDAY, LOGIN, EMAIL) VALUES (?, ?, ?, ?);"),

    INSERT_TABLE_USERS_All_COLUMN("INSERT INTO USERS (ID, NAME, BIRTHDAY, LOGIN, EMAIL) VALUES (?, ?, ?, ?, ?);"),

    UPDATE_TABLE_USERS__ROW_BY_ID("UPDATE USERS SET NAME = ?, BIRTHDAY = ?, LOGIN = ?, EMAIL = ? WHERE  ID = ?;"),

    DELETE_TABLE_USERS__ALL_ROWS("DELETE FROM USERS;"),

    DELETE_TABLE_USERS__ROW_BY_ID("DELETE FROM USERS WHERE ID = ?;"),

    DELETE_TABLE_USERS__ROW_BY_LOGIN("DELETE FROM USERS WHERE LOGIN = ?;");

    @Getter
    private final String template;

    RequestsTableUsers(String template) {
        this.template = template;
    }
}
