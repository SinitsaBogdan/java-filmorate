package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableTotalUserFriends {

    SELECT_ALL_FRIENDS_BY_USER_ID_ON_MODEL_USER(
            "SELECT * FROM USERS " +
                    "WHERE ID IN (" +
                        "SELECT FRIEND_ID FROM TOTAL_USER_FRIENDS " +
                        "WHERE USER_ID = ? AND STATUS_ID = 2" +
                    ") " +
                    "ORDER BY ID ASC;"
    ),

    SELECT_ALL_FRIENDS_COMMON_BY_USER_ID_AND_FRIEND_ID_ON_MODEL_USER(
            "SELECT * FROM USERS " +
                    "WHERE ID IN (" +
                        "SELECT FRIEND_ID " +
                        "FROM TOTAL_USER_FRIENDS " +
                        "WHERE USER_ID = ? AND STATUS_ID = 2 AND FRIEND_ID IN (" +
                            "SELECT FRIEND_ID " +
                            "FROM TOTAL_USER_FRIENDS " +
                            "WHERE USER_ID = ? AND STATUS_ID = 2" +
                        ")" +
                    ");"
    ),

    SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS(
            "SELECT * FROM TOTAL_USER_FRIENDS;"
    ),

    SELECT_TABLE_TOTAL_USER_FRIENDS__ROW_BY_USER_ID_AND_FRIEND_ID(
            "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE USER_ID = ? AND FRIEND_ID = ?;"
    ),

    SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS_BY_USER_ID(
            "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE USER_ID = ?;"
    ),

    SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS_BY_FRIEND_ID(
            "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE FRIEND_ID = ?;"
    ),

    SELECT_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS_BY_STATUS_ID(
            "SELECT * FROM TOTAL_USER_FRIENDS " +
                    "WHERE STATUS_ID = ?;"
    ),

    INSERT_TABLE_TOTAL_USER_FRIENDS__ALL_COLUMN(
            "INSERT INTO TOTAL_USER_FRIENDS (USER_ID, FRIEND_ID, STATUS_ID) VALUES(?, ?, ?);"
    ),

    UPDATE_TABLE_TOTAL_USER_FRIENDS__STATUS_BY_USER_ID_AND_FRIEND_ID(
            "UPDATE TOTAL_USER_FRIENDS " +
                    "SET STATUS_ID = ? " +
                    "WHERE USER_ID = ? AND FRIEND_ID = ?;"
    ),

    DELETE_TABLE_TOTAL_USER_FRIENDS__ALL_ROWS(
            "DELETE FROM TOTAL_USER_FRIENDS;"
    ),

    DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_USER_ID(
            "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE USER_ID = ?;"
    ),

    DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_FRIEND_ID(
            "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE FRIEND_ID = ?;"
    ),

    DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_STATUS_ID(
            "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE STATUS_ID = ?;"
    ),

    DELETE_TABLE_TOTAL_USER_FRIENDS__ROW_BY_USER_ID_AND_FRIEND_ID(
            "DELETE FROM TOTAL_USER_FRIENDS " +
                    "WHERE USER_ID = ? AND FRIEND_ID = ?;"
    );

    @Getter
    private final String template;

    RequestsTableTotalUserFriends(String template) {
        this.template = template;
    }
}
