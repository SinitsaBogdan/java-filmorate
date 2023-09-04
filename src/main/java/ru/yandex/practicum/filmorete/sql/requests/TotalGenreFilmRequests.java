package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum TotalGenreFilmRequests {
    SELECT_ALL__TOTAL_GENRE_FILM__FILM_GENRE(
        "SELECT * " +
            "FROM TOTAL_GENRE_FILM " +
            "WHERE film_id = ? AND genre_id = ?;"
    ),

    SELECT_ALL__GENRE__FILM(
        "SELECT * " +
            "FROM ROSTER_GENRE " +
            "WHERE id IN (" +
                "SELECT genre_id " +
                "FROM TOTAL_GENRE_FILM " +
                "WHERE film_id = ?" +
            ");"
    ),

    SELECT_ALL__TOTAL_GENRE_FILM(
        "SELECT * " +
            "FROM TOTAL_GENRE_FILM;"
    ),

    SELECT_ALL__TOTAL_GENRE_FILM__FILM(
        "SELECT * " +
            "FROM TOTAL_GENRE_FILM " +
            "WHERE film_id = ?;"
    ),

    SELECT_ALL__TOTAL_GENRE_FILM__GENRE(
        "SELECT * " +
            "FROM TOTAL_GENRE_FILM " +
            "WHERE genre_id = ?;"
    ),

    INSERT_ONE__TOTAL_GENRE_FILM__FILM_GENRE(
        "INSERT INTO TOTAL_GENRE_FILM (film_id, genre_id) " +
            "VALUES(?, ?);"
    ),

    DELETE_ALL__TOTAL_GENRE_FILM(
        "DELETE FROM TOTAL_GENRE_FILM;"
    ),

    DELETE_ONE__TOTAL_GENRE_FILM__FILM_GENRE(
        "DELETE FROM TOTAL_GENRE_FILM " +
            "WHERE film_id = ? AND genre_id = ?;"
    ),

    DELETE_ONE__TOTAL_GENRE_FILM__FILM(
        "DELETE FROM TOTAL_GENRE_FILM " +
            "WHERE film_id = ?;"
    ),

    DELETE_ONE__TOTAL_GENRE_FILM__GENRE(
        "DELETE FROM TOTAL_GENRE_FILM " +
            "WHERE genre_id = ?;"
    );

    private final String sql;

    TotalGenreFilmRequests(String sql) {
        this.sql = sql;
    }
}
