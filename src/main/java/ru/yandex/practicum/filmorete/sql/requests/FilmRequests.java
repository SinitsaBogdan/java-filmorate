package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum FilmRequests {
    SELECT_ALL__FILMS(
        "SELECT " +
                "f.id AS film_id, " +
                "f.NAME AS film_name, " +
                "f.description AS film_description, " +
                "f.release_date AS film_release_date, " +
                "f.duration AS film_duration, " +
                "f.rate AS film_rate, " +
                "r.id AS mpa_id, " +
                "r.name AS mpa_name, " +
                "g.id AS genre_id, " +
                "g.name AS genre_name, " +
                "d.id AS director_id, " +
                "d.name AS director_name " +
            "FROM FILMS AS f " +
            "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "ORDER BY f.id;"
    ),

    SELECT_ALL__FILM__ID(
        "SELECT " +
                "f.id AS film_id, " +
                "f.name AS film_name, " +
                "f.description AS film_description, " +
                "f.release_date AS film_release_date, " +
                "f.duration AS film_duration, " +
                "f.rate AS film_rate, " +
                "r.id AS mpa_id, " +
                "r.name AS mpa_name, " +
                "g.id AS genre_id, " +
                "g.name AS genre_name, " +
                "d.id AS director_id, " +
                "d.name AS director_name " +
            "FROM FILMS AS f " +
            "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE f.id = ? " +
            "ORDER BY f.id;"
    ),

    SELECT_MAX_ID__FILM(
        "SELECT MAX(ID) AS id FROM FILMS;"
    ),

    INSERT_ONE__FILM__ID_MPA_ID_NAME_DESCRIPTION_RELEASE_DATE_DURATION(
        "INSERT INTO FILMS (id, mpa_id, name, description, release_date, duration) " +
            "VALUES (?, ?, ?, ?, ?, ?);"
    ),

    INSERT_ONE__FILM__MPA_ID_NAME_DESCRIPTION_RELEASE_DATE_DURATION(
        "INSERT INTO FILMS (mpa_id, name, description, release_date, duration) " +
            "VALUES (?, ?, ?, ?, ?);"
    ),

    UPDATE_ONE__FILM__SET_MPA_ID_NAME_DESCRIPTION_RELEASE_DATE_DURATION__ID(
        "UPDATE FILMS " +
            "SET " +
                "mpa_id = ?, " +
                "name = ?, " +
                "description = ?, " +
                "release_date = ?, " +
                "duration = ? " +
            "WHERE id = ?;"
    ),

    UPDATE_ONE__FILM__SET_MPA_ID_NAME_DESCRIPTION_RELEASE_DATE_DURATION__NAME(
        "UPDATE FILMS " +
            "SET " +
                "mpa_id = ?, " +
                "name = ?, " +
                "description = ?, " +
                "release_date = ?, " +
                "duration = ? " +
            "WHERE name = ?;"
    ),
    DELETE_ALL__FILMS(
        "DELETE FROM FILMS;"
    ),

    DELETE_ONE__FILMS__ID(
        "DELETE FROM FILMS " +
            "WHERE id = ?;"
    ),

    DELETE_ONE__FILMS__NAME(
        "DELETE FROM FILMS " +
            "WHERE name = ?;"
    ),

    DELETE_ONE__FILMS__RELEASE_DATE(
        "DELETE FROM FILMS " +
            "WHERE release_date = ?;"
    ),

    DELETE_ONE__FILMS__DURATION(
        "DELETE FROM FILMS " +
            "WHERE duration = ?;"
    ),

    DELETE_ONE__FILMS__MPA(
        "DELETE FROM FILMS " +
            "WHERE mpa_id = ?;"
    );

    private final String sql;

    FilmRequests(String sql) {
        this.sql = sql;
    }
}
