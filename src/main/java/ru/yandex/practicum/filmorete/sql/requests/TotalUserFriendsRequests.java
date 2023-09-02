package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum TotalUserFriendsRequests {
    SELECT_ALL__USERS__ID(
        "SELECT * " +
            "FROM USERS " +
            "WHERE id IN (" +
                "SELECT friend_id " +
                "FROM TOTAL_USER_FRIENDS " +
                "WHERE user_id = ? AND status = 'CONFIRMED'" +
            ") " +
            "ORDER BY id ASC;"
    ),

    SELECT_ALL__USER_COMMON_FRIENDS__USER_FRIEND(
        "SELECT * FROM USERS " +
            "WHERE id IN (" +
                "SELECT friend_id " +
                "FROM TOTAL_USER_FRIENDS " +
                "WHERE user_id = ? AND status = 'CONFIRMED' AND friend_id IN (" +
                    "SELECT friend_id " +
                    "FROM TOTAL_USER_FRIENDS " +
                    "WHERE user_id = ? AND status = 'CONFIRMED'" +
                ")" +
            ");"
    ),

    SELECT_ALL__TOTAL_USER_FRIENDS(
        "SELECT * FROM TOTAL_USER_FRIENDS;"
    ),

    SELECT_ALL__TOTAL_USER_FRIENDS__USER(
        "SELECT * " +
            "FROM TOTAL_USER_FRIENDS " +
            "WHERE user_id = ?;"
    ),

    SELECT_ALL__TOTAL_USER_FRIENDS__FRIEND(
        "SELECT * " +
            "FROM TOTAL_USER_FRIENDS " +
            "WHERE friend_id = ?;"
    ),

    SELECT_ALL__TOTAL_USER_FRIENDS__STATUS(
        "SELECT * " +
            "FROM TOTAL_USER_FRIENDS " +
            "WHERE status = ?;"
    ),

    SELECT_ONE__TOTAL_USER_FRIENDS__USER_FRIEND(
        "SELECT * " +
            "FROM TOTAL_USER_FRIENDS " +
            "WHERE user_id = ? AND friend_id = ?;"
    ),

    INSERT_ONE__TOTAL_USER_FRIENDS__USER_FRIEND_STATUS(
        "INSERT INTO TOTAL_USER_FRIENDS (user_id, friend_id, status) " +
            "VALUES(?, ?, ?);"
    ),

    UPDATE_ONE_TOTAL_USER_FRIENDS__SET_STATUS__USER_FRIEND(
        "UPDATE TOTAL_USER_FRIENDS " +
            "SET status = ? " +
            "WHERE user_id = ? AND friend_id = ?;"
    ),

    DELETE_ALL__TOTAL_USER_FRIENDS(
        "DELETE FROM TOTAL_USER_FRIENDS;"
    ),

    DELETE_ONE__TOTAL_USER_FRIENDS__USER_FRIEND(
        "DELETE FROM TOTAL_USER_FRIENDS " +
            "WHERE user_id = ? AND friend_id = ?;"
    ),

    DELETE_ALL__TOTAL_USER_FRIENDS__USER(
        "DELETE FROM TOTAL_USER_FRIENDS " +
            "WHERE user_id = ?;"
    ),

    DELETE_ALL__TOTAL_USER_FRIENDS__FRIEND(
        "DELETE FROM TOTAL_USER_FRIENDS " +
            "WHERE friend_id = ?;"
    ),

    DELETE_ALL__TOTAL_USER_FRIENDS__STATUS(
        "DELETE FROM TOTAL_USER_FRIENDS " +
            "WHERE status = ?;"
    );

    private final String sql;

    TotalUserFriendsRequests(String sql) {
        this.sql = sql;
    }
}
