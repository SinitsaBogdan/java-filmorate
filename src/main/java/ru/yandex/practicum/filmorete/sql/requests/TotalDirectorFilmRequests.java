package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum TotalDirectorFilmRequests {
    SELECT_ALL__TOTAL_DIRECTOR_FILMS(
        "SELECT " +
            "t.film_id AS filmId, " +
            "t.director_id AS directorId " +
            "FROM TOTAL_FILM_DIRECTOR AS t;"
    ),

    SELECT__ONE_TOTAL_DIRECTOR_FILM__DIRECTOR_ID(
        "SELECT * FROM TOTAL_FILM_DIRECTOR " +
            "WHERE director_id = ?;"
    ),

    SELECT_ALL__TOTAL_DIRECTOR_FILMS__FILM_ID(
        "SELECT * FROM TOTAL_FILM_DIRECTOR " +
            "WHERE film_id = ?;"
    ),

    INSERT_ONE__TOTAL_DIRECTOR_FILM__FILM_ID_DIRECTOR_ID(
        "INSERT INTO TOTAL_FILM_DIRECTOR (film_id, director_id) " +
            "VALUES (?, ?);"
    ),

    UPDATE_ONE__TOTAL_DIRECTOR_FILM__FILM_ID_DIRECTOR_ID(
        "UPDATE TOTAL_FILM_DIRECTOR " +
            "SET film_id = ? " +
            "WHERE director_id = ?;"
    ),

    DELETE_ONE_TOTAL_DIRECTOR_FILM__FILM_ID(
        "DELETE FROM TOTAL_FILM_DIRECTOR " +
            "WHERE film_id = ?;"
    ),

    SELECT_ALL__FILMS__POPULAR_SORT_DIRECTOR(
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
                "d.name AS director_name, " +
                "EXTRACT(YEAR FROM f.release_date) AS release_year, " +
                "( " +
                    "SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id" +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE f.id IN (" +
                "SELECT film_id FROM " +
                "TOTAL_FILM_DIRECTOR WHERE director_id = ?" +
            ") " +
            "ORDER BY (" +
                "SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l " +
                "WHERE l.film_id = f.id" +
            ") DESC;"
        ),

    SELECT_ALL__FILMS_SORT_YEAR_DIRECTOR(
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
                "d.name AS director_name, " +
                "EXTRACT(YEAR FROM f.release_date) AS release_year, " +
                "( " +
                    "SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id " +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE f.id IN ( " +
                "SELECT film_id FROM " +
                "TOTAL_FILM_DIRECTOR WHERE director_id = ?" +
            ") " +
            "ORDER BY " +
                "EXTRACT(YEAR " +
                "FROM f.release_date" +
            ") ASC;"
    ),

    DELETE_ALL__TOTAL_FILM_DIRECTOR(
        "DELETE FROM TOTAL_FILM_DIRECTOR;"
    );

    private final String sql;

    TotalDirectorFilmRequests(String sql) {
        this.sql = sql;
    }
}
