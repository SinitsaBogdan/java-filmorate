package ru.yandex.practicum.filmorete.sql.requests;

import lombok.Getter;

@Getter
public enum TotalFilmLikeRequests {

    SELECT_ONE__TOTAL_FILM_LIKE__FILM_USER(
            "SELECT * FROM TOTAL_FILM_LIKE " +
                "WHERE film_id = ? AND user_id = ?;"
    ),

    SELECT_ALL__FILMS_POPULAR__LIMIT(
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
                "( " +
                "SELECT COUNT(*) " +
                "FROM TOTAL_FILM_LIKE AS l " +
                "WHERE l.film_id = f.id " +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE f.id IN ( " +
                "SELECT f.id FROM FILMS AS f " +
                "ORDER BY (" +
                    "SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id " +
                ") DESC " +
                "LIMIT ? " +
            ") "
    ),

    SELECT_ALL__FILMS_POPULAR__LIMIT_GENRE(
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
                "( " +
                "SELECT COUNT(*) " +
                "FROM TOTAL_FILM_LIKE AS l " +
                "WHERE l.film_id = f.id " +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE f.id IN (" +
                "SELECT f.id FROM FILMS AS f " +
                "ORDER BY (" +
                    "SELECT COUNT(*) " +
                    "FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id " +
                ") DESC " +
                "LIMIT ? " +
            ") " +
            "AND f.id IN (" +
                "SELECT film_id " +
                "FROM TOTAL_GENRE_FILM " +
                "WHERE genre_id = ?" +
            ");"
    ),

    SELECT_ALL__FILMS_POPULAR__LIMIT_YEAR(
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
                "( " +
                    "SELECT COUNT(*) " +
                    "FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id " +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE f.id IN ( " +
                "SELECT f.id FROM FILMS AS f " +
                "ORDER BY (" +
                    "SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id" +
                ") DESC " +
                "LIMIT ? " +
            ") " +
            "AND f.id IN (" +
                "SELECT f.id " +
                "FROM FILMS AS f " +
                "WHERE EXTRACT(YEAR FROM f.release_date) = ?" +
            ");"
    ),

    SELECT_ALL__FILMS_POPULAR__SORT_YEAR(
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
                "( " +
                    "SELECT COUNT(*) " +
                    "FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id " +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "LEFT JOIN TOTAL_FILM_DIRECTOR AS td ON f.id = td.film_id " +
            "LEFT JOIN DIRECTORS AS d ON td.director_id = d.id " +
            "WHERE " +
                "f.id IN (" +
                    "SELECT f.id FROM FILMS AS f " +
                    "ORDER BY (" +
                        "SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l " +
                        "WHERE l.film_id = f.id" +
                    ") DESC " +
                "LIMIT ? " +
            ") " +
            "AND f.id IN (" +
                "SELECT tf.film_id " +
                "FROM TOTAL_GENRE_FILM AS tf " +
                "WHERE genre_id = ?" +
            ") " +
            "AND f.id IN (" +
                "SELECT f.id " +
                "FROM FILMS AS f " +
                "WHERE EXTRACT(YEAR FROM f.release_date) = ?" +
            ");"
    ),

    SELECT_ALL__USERS_TOTAL_FILM_LIKE__FILM(
        "SELECT * " +
            "FROM USERS " +
            "WHERE id IN (" +
                "SELECT user_id " +
                "FROM TOTAL_FILM_LIKE " +
                "WHERE film_id = ?" +
            ");"
    ),

    SELECT_ALL__FILMS_TOTAL_FILM_LIKE__USER(
        "SELECT " +
                "f.id AS film_id, " +
                "f.name AS film_name, " +
                "f.description AS film_description, " +
                "f.release_date AS film_release_date, " +
                "f.duration AS film_duration, " +
                "r.id AS mpa_id, " +
                "r.name AS mpa_name, " +
                "g.id AS genre_id, " +
                "g.name AS genre_name, " +
                "(" +
                    "SELECT COUNT(*) " +
                    "FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id" +
                ") AS size_like " +
            "FROM FILMS AS f " +
            "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "WHERE f.id IN (" +
                "SELECT film_id " +
                "FROM TOTAL_FILM_LIKE " +
                "WHERE user_id = ?" +
            ");"
    ),

    SELECT_ALL__TOTAL_FILM_LIKE(
        "SELECT * FROM TOTAL_FILM_LIKE;"
    ),

    SELECT_ALL__TOTAL_FILM_LIKE__FILM(
        "SELECT * " +
            "FROM TOTAL_FILM_LIKE " +
            "WHERE film_id = ?;"
    ),

    SELECT_ALL__TOTAL_FILM_LIKE__USER(
        "SELECT * " +
            "FROM TOTAL_FILM_LIKE " +
            "WHERE user_id = ?;"
    ),

    SELECT_ALL__COMMON_FILMS(
        "SELECT f.id AS film_id, " +
                "f.name AS film_name, " +
                "f.description AS film_description, " +
                "f.release_date AS film_release_date, " +
                "f.duration AS film_duration, " +
                "f.rate AS film_rate, " +
                "r.id AS mpa_id, " +
                "r.name AS mpa_name, " +
                "g.id AS genre_id, " +
                "g.name AS genre_name " +
            "FROM FILMS AS f " +
            "LEFT JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
            "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
            "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
            "WHERE f.id IN (" +
                "SELECT f.id FROM FILMS AS f " +
                "ORDER BY (" +
                    "SELECT COUNT(*) " +
                    "FROM TOTAL_FILM_LIKE AS l " +
                    "WHERE l.film_id = f.id" +
                ") DESC" +
            ")" +
            "AND f.id IN ( " +
                "SELECT film_id " +
                "FROM total_film_like " +
                "WHERE user_id = ? AND film_id IN ( " +
                    "SELECT film_id " +
                    "FROM total_film_like " +
                    "WHERE user_id = ? " +
                ")" +
            ")"
    ),

    SELECT_ALL__RECOMMENDATION(
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
            "WHERE f.id IN ( " +
                "SELECT tlf.film_id " +
                "FROM TOTAL_FILM_LIKE AS tlf " +
                "WHERE tlf.user_id = ? " +
                ") " +
            "AND NOT f.id IN ( " +
                "SELECT tlf.film_id " +
                "FROM TOTAL_FILM_LIKE AS tlf " +
                "WHERE tlf.user_id = ? " +
            ") " +
            "ORDER BY f.id;"
    ),

    SELECT_ALL__USERS_FILMS(
        "SELECT user_id, film_id " +
            "FROM TOTAL_FILM_LIKE"
    ),

    SELECT_USERS_BY_COUNT_FILM_LIKES(
            "SELECT tfl2.user_id, COUNT(tfl2.user_id) AS common_likes " +
                "FROM TOTAL_FILM_LIKE AS tfl2 " +
                "WHERE tfl2.film_id IN (" +
                    "SELECT tfl.film_id " +
                    "FROM TOTAL_FILM_LIKE AS tfl " +
                    "WHERE tfl.user_id = ?) " +
                "AND NOT tfl2.user_id = ? " +
                "GROUP BY tfl2.user_id " +
                "ORDER BY common_likes DESC " +
                "LIMIT 10"
    ),

    INSERT_ONE__TOTAL_FILM_LIKE__FILM_USER(
        "INSERT INTO TOTAL_FILM_LIKE (film_id, user_id) " +
            "VALUES(?, ?);"
    ),

    UPDATE_ONE__TOTAL_FILM_LIKE__SET_FILM_USER__FILM_USER(
        "UPDATE TOTAL_FILM_LIKE " +
            "SET film_id = ?, user_id = ? " +
            "WHERE film_id = ? AND user_id = ?;"
    ),

    DELETE_ALL__TOTAL_FILM_LIKE(
        "DELETE FROM TOTAL_FILM_LIKE;"
    ),

    DELETE_ONE__TOTAL_FILM_LIKE__FILM_USER(
        "DELETE " +
            "FROM TOTAL_FILM_LIKE " +
            "WHERE film_id = ? AND user_id = ?;"
    );

    private final String sql;

    TotalFilmLikeRequests(String sql) {
        this.sql = sql;
    }
}
