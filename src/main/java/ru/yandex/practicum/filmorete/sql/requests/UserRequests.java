package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum UserRequests {
    SELECT_MAX_ID__ID(
        "SELECT MAX(id) AS last_id FROM USERS;"
    ),

    SELECT_ALL__USERS(
        "SELECT * FROM USERS;"
    ),

    SELECT_ONE__USER__ID(
        "SELECT * " +
            "FROM USERS " +
            "WHERE id = ?;"
    ),

    SELECT_ONE__USER__EMAIL(
        "SELECT * " +
            "FROM USERS " +
            "WHERE email = ?;"
    ),

    INSERT_ONE__USER__FULL(
        "INSERT INTO USERS (id, name, birthday, login, email) " +
            "VALUES (?, ?, ?, ?, ?);"
    ),

    INSERT_ONE__USER__NAME_BIRTHDAY_LOGIN_EMAIL(
        "INSERT INTO USERS (name, birthday, login, email) " +
            "VALUES (?, ?, ?, ?);"
    ),

    UPDATE_ONE__USER__SET_NAME_BIRTHDAY_LOGIN_EMAIL__ID(
        "UPDATE USERS " +
            "SET name = ?, birthday = ?, login = ?, email = ? " +
            "WHERE  id = ?;"
    ),

    DELETE_ALL__USERS(
        "DELETE FROM USERS;"
    ),

    DELETE_ONE__USER__ID(
        "DELETE " +
            "FROM USERS " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__USER__LOGIN(
        "DELETE " +
            "FROM USERS " +
            "WHERE login = ?;"
    );

    private final String sql;

    UserRequests(String sql) {
        this.sql = sql;
    }
}
