package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableTotalFilmLike {

    SELECT_TABLE_TOTAL_FILM_LIKE_POPULAR_FILMS_AND_ORDER_BY_AND_LIMIT_ON_MODEL_FILM(
            "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ENUM_MPA.ID AS MPA_ID, " +
                        "ENUM_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION, " +
                        "(" +
                            "SELECT COUNT(*) " +
                            "FROM TOTAL_FILM_LIKE " +
                            "WHERE TOTAL_FILM_LIKE.FILM_ID = FILMS.ID" +
                        ") AS SIZE_LIKE " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ENUM_MPA AS ENUM_MPA ON FILMS.MPA_ID = ENUM_MPA.ID " +
                    "ORDER BY SIZE_LIKE DESC " +
                    "LIMIT ?;"
    ),
    SELECT_ALL_USERS_TO_LIKE_FILM_ON_MODEL_USER(
            "SELECT * " +
                    "FROM USERS " +
                    "WHERE ID IN (" +
                        "SELECT USER_ID FROM TOTAL_FILM_LIKE " +
                        "WHERE FILM_ID = ?" +
                    ");"
    ),
    SELECT_ALL_FILMS_TO_LIKE_FILM_ON_MODEL_FILMS(
            "SELECT " +
                        "FILMS.ID AS ID, " +
                        "ENUM_MPA.ID AS MPA_ID, " +
                        "ENUM_MPA.NAME AS MPA_NAME, " +
                        "FILMS.NAME AS NAME, " +
                        "FILMS.DESCRIPTION AS DESCRIPTION, " +
                        "FILMS.RELEASE_DATE AS RELEASE_DATE, " +
                        "FILMS.DURATION AS DURATION, " +
                        "(" +
                            "SELECT COUNT(*) " +
                            "FROM TOTAL_FILM_LIKE " +
                            "WHERE TOTAL_FILM_LIKE.FILM_ID = FILMS.ID" +
                        ") AS SIZE_LIKE " +
                    "FROM FILMS AS FILMS " +
                    "INNER JOIN ENUM_MPA AS ENUM_MPA ON FILMS.MPA_ID = ENUM_MPA.ID " +
                    "WHERE FILMS.ID IN (" +
                        "SELECT FILM_ID FROM TOTAL_FILM_LIKE " +
                        "WHERE USER_ID = ?" +
                    ");"
    ),
    SELECT_TABLE_TOTAL_FILM_LIKE__ALL_ROWS(
            "SELECT * FROM TOTAL_FILM_LIKE;"
    ),
    SELECT_TABLE_TOTAL_FILM_LIKE__ROW_BY_FILM_ID(
            "SELECT * FROM TOTAL_FILM_LIKE " +
                    "WHERE FILM_ID = ?;"
    ),
    SELECT_TABLE_TOTAL_FILM_LIKE__ROW_BY_USER_ID(
            "SELECT * FROM TOTAL_FILM_LIKE " +
                    "WHERE USER_ID = ?;"
    ),
    INSERT_TABLE_TOTAL_FILM_LIKE(
            "INSERT INTO TOTAL_FILM_LIKE (FILM_ID, USER_ID) VALUES(?, ?);"
    ),
    UPDATE_TABLE_TOTAL_FILM_LIKE__ROW_BY_FILM_ID_AND_USER_ID(
            "UPDATE TOTAL_FILM_LIKE " +
                    "SET " +
                        "FILM_ID = ?, " +
                        "USER_ID = ? " +
                    "WHERE FILM_ID = ? AND USER_ID = ?;"
    ),
    DELETE_TABLE_TOTAL_FILM_LIKE__ALL_ROWS(
            "DELETE FROM TOTAL_FILM_LIKE;"
    ),
    DELETE_TABLE_TOTAL_FILM_LIKE__ROW_BY_FILM_ID_AND_USER_ID(
            "DELETE FROM TOTAL_FILM_LIKE " +
                    "WHERE FILM_ID = ? AND USER_ID = ?;"
    );

    @Getter
    private final String template;

    RequestsTableTotalFilmLike(String template) {
        this.template = template;
    }
}
