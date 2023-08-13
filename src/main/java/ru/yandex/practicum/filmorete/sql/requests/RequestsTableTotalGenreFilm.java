package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

public enum RequestsTableTotalGenreFilm {

    SELECT_NAME_GENRE_FILM__ROWS_BY_FILM_ID(
            "SELECT * " +
                    "FROM ROSTER_GENRE " +
                    "WHERE ID IN (" +
                        "SELECT GENRE_ID " +
                        "FROM TOTAL_GENRE_FILM " +
                        "WHERE FILM_ID = ?" +
                    ");"
    ),

    SELECT_TABLE_TOTAL_GENRE_FILM__ALL_ROWS(
            "SELECT * FROM TOTAL_GENRE_FILM;"
    ),

    SELECT_TABLE_TOTAL_GENRE_FILM__ROW_BY_FILM_ID_AND_GENRE_ID(
            "SELECT * FROM TOTAL_GENRE_FILM " +
                    "WHERE FILM_ID = ? AND GENRE_ID = ?;"
    ),

    SELECT_TABLE_TOTAL_GENRE_FILM__ROWS_BY_FILM_ID(
            "SELECT * FROM TOTAL_GENRE_FILM " +
                    "WHERE FILM_ID = ?;"
    ),

    SELECT_TABLE_TOTAL_GENRE_FILM__ROWS_BY_GENRE_ID(
            "SELECT * FROM TOTAL_GENRE_FILM " +
                    "WHERE GENRE_ID = ?;"
    ),

    INSERT_TABLE_TOTAL_GENRE_FILM(
            "INSERT INTO TOTAL_GENRE_FILM (FILM_ID, GENRE_ID) VALUES(?, ?);"),

    DELETE_TABLE_TOTAL_GENRE_FILM__ALL_ROWS(
            "DELETE FROM TOTAL_GENRE_FILM;"),

    DELETE_TABLE_TOTAL_GENRE_FILM__ROW_BY_FILM_ID(
            "DELETE FROM TOTAL_GENRE_FILM " +
                    "WHERE FILM_ID = ?;"
    ),

    DELETE_TABLE_TOTAL_GENRE_FILM__ROW_BY_GENRE_ID(
            "DELETE FROM TOTAL_GENRE_FILM " +
                    "WHERE GENRE_ID = ?;"
    ),

    DELETE_TABLE_TOTAL_GENRE_FILM__ROW_BY_FILM_ID_AND_GENRE_ID(
            "DELETE FROM TOTAL_GENRE_FILM " +
                    "WHERE FILM_ID = ? AND GENRE_ID = ?;"
    );

    @Getter
    private final String template;

    RequestsTableTotalGenreFilm(String template) {
        this.template = template;
    }
}
