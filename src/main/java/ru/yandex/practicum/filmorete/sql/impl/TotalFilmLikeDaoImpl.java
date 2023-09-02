package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;

import java.util.*;

// TODO Нужно будет переименовать класс
//  на TotalFilmEstimationDaoImpl

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalFilmLikeDaoImpl implements TotalFilmLikeDao {

    // TODO
    //  + Переименовать методы
    //  по примеру -> findFilmToEstimationUser()
    //  Like заменить на Estimation

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> findPopularFilms(Integer limit) {
        // TODO
        //  Сортировку по количеству лайков
        //  заменить на сортировку по рейтингу
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
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
                        "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC " +
                        "LIMIT ? " +
                        ") ",
                limit
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(filmId).addDirector(director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findPopularFilms(Integer limit, Integer searchGenreId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
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
                        "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC " +
                        "LIMIT ? " +
                        ") " +
                        "AND f.id IN (" +
                        "SELECT film_id " +
                        "FROM TOTAL_GENRE_FILM " +
                        "WHERE genre_id = ?" +
                        ");",
                limit, searchGenreId
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(filmId).addDirector(director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findPopularFilmsSortByYear(Integer limit, Integer searchYear) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
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
                        "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC " +
                        "LIMIT ? " +
                        ") " +
                        "AND f.id IN (" +
                        "SELECT f.id " +
                        "FROM FILMS AS f " +
                        "WHERE EXTRACT(YEAR FROM f.release_date) = ?" +
                        ");",
                limit, searchYear
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(filmId).addDirector(director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findPopularFilms(Integer limit, Integer searchGenreId, Integer searchYear) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
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
                        "ORDER BY ( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) DESC " +
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
                        ");",
                limit, searchGenreId, searchYear
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(filmId).addDirector(director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<User> findUserToLikeFilm(Long filmId) {
        List<User> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * " +
                        "FROM USERS " +
                        "WHERE id IN (" +
                        "SELECT user_id FROM TOTAL_FILM_LIKE " +
                        "WHERE film_id = ?" +
                        ");",
                filmId
        );
        while (rows.next()) result.add(FactoryModel.buildUser(rows));
        return result;
    }

    @Override
    public List<Film> findFilmToLikeUser(Long userId) {
        // TODO
        //  Сортировку по количеству лайков
        //  заменить на сортировку по рейтингу
        //  везде где есть List<Film> с сортировкой
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
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
                        "( SELECT COUNT(*) FROM TOTAL_FILM_LIKE AS l WHERE l.film_id = f.id ) AS size_like " +
                        "FROM FILMS AS f " +
                        "INNER JOIN ROSTER_MPA AS r ON f.mpa_id = r.id " +
                        "LEFT JOIN TOTAL_GENRE_FILM AS t ON f.id = t.film_id " +
                        "LEFT JOIN ROSTER_GENRE AS g ON t.genre_id = g.id " +
                        "WHERE f.id IN (" +
                        "SELECT film_id " +
                        "FROM TOTAL_FILM_LIKE " +
                        "WHERE user_id = ?" +
                        ");",
                userId
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<TotalLikeFilm> findAllTotalFilmLike() {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE;"
        );
        while (rows.next()) result.add(FactoryModel.buildTotalLikeFilm(rows));
        return result;
    }

    @Override
    public List<TotalLikeFilm> findAllTotalFilmLikeByFilmId(Long filmId) {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE film_id = ?;",
                filmId
        );
        while (rows.next()) result.add(FactoryModel.buildTotalLikeFilm(rows));
        return result;
    }

    @Override
    public List<TotalLikeFilm> findAllTotalFilmLikeByUserId(Long userId) {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_LIKE WHERE user_id = ?;",
                userId
        );
        while (rows.next()) result.add(FactoryModel.buildTotalLikeFilm(rows));
        return result;
    }

    @Override
    public List<Film> findCommonFilms(Long firstUserId, Long secondUserId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
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
                        ")",
                firstUserId, secondUserId
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> getRecommendationForUser(Long userId) {
        Optional<Long> friendByFilmsId = findFriendByFilmsId(userId);
        if (friendByFilmsId.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT f.id AS film_id, " +
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
                        "ORDER BY f.id;",
                friendByFilmsId.get(), userId
        );
        while (rows.next()) {
            Long filmId = rows.getLong("FILM_ID");
            Integer genreId = rows.getInt("GENRE_ID");
            String genreName = rows.getString("GENRE_NAME");
            Long dirId = rows.getLong("DIRECTOR_ID");
            String dirName = rows.getString("DIRECTOR_NAME");
            if (!result.containsKey(filmId)) {
                Film film = FactoryModel.buildFilm(rows);
                result.put(filmId, film);
            }
            if (genreName != null) {
                Genre genre = Genre.builder().id(genreId).name(genreName).build();
                result.get(filmId).addGenre(genre);
            }
            if (dirName != null) {
                Director director = Director.builder().id(dirId).name(dirName).build();
                result.get(filmId).addDirector(director);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    private Optional<Long> findFriendByFilmsId(Long userId) {
        Map<Long, Set<Long>> userLikeToFilm = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT user_id, film_id FROM TOTAL_FILM_LIKE");
        while (rows.next()) {
            Long userLikeId = rows.getLong("user_id");
            Long filmId = rows.getLong("film_id");
            if (!userLikeToFilm.containsKey(userLikeId)) {
                Set<Long> idLikedFilms = new HashSet<>();
                idLikedFilms.add(filmId);
                userLikeToFilm.put(userLikeId, idLikedFilms);
            } else {
                Set<Long> idLikedFilms = userLikeToFilm.get(userLikeId);
                idLikedFilms.add(filmId);
            }
        }
        if (!userLikeToFilm.containsKey(userId)) {
            return Optional.empty();
        }
        Map<Long, Integer> userPoints = new HashMap<>();
        Set<Long> userIdFilms = userLikeToFilm.get(userId);
        userLikeToFilm.remove(userId);
        for (Long id : userLikeToFilm.keySet()) {
            Set<Long> filmsIds = userLikeToFilm.get(id);
            filmsIds.retainAll(userIdFilms);
            userPoints.put(id, filmsIds.size());
        }
        Optional<Map.Entry<Long, Integer>> entry = userPoints.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
        if (entry.isEmpty() || entry.get().getValue() == 0) {
            return Optional.empty();
        }
        return entry.map(Map.Entry::getKey);
    }

    @Override
    public void insert(Long filmId, Long userId, Integer estimation) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_FILM_LIKE (film_id, user_id, estimation) " +
                    "VALUES(?, ?, ?);",
                filmId, userId, estimation
        );
    }

    @Override
    public void recalculationPositive(Long filmId){
        Double newRate = jdbcTemplate.queryForObject(
            "SELECT AVG(estimation) " +
                "FROM TOTAL_FILM_LIKE " +
                "WHERE film_id = ?;",
            Double.class, filmId
        );
        if(newRate != null){
            jdbcTemplate.update(
                "UPDATE FILMS " +
                    "SET rate = ? " +
                    "WHERE id = ?;",
                newRate, filmId
            );
        }
        System.out.println(filmId + ", " + newRate);
    }

    @Override
    // TODO
    //  Добавить параметр в метод
    //  Integer estimation
    public void update(Long searchFilmId, Long searchUserId, Integer estimation) {
        jdbcTemplate.update(
                "UPDATE TOTAL_FILM_LIKE SET estimation = ? " +
                    "WHERE film_id = ? AND user_id = ?;",
                estimation, searchFilmId, searchUserId
        );
    }

    @Override
    public void delete() {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_FILM_LIKE;"
        );
    }

    @Override
    public void delete(Long filmId, Long userId) {
        jdbcTemplate.update(
                "DELETE FROM TOTAL_FILM_LIKE " +
                        "WHERE film_id = ? AND user_id = ?;",
                filmId, userId
        );
    }
}