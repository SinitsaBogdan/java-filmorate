package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;

import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.TotalFilmLikeRequests.*;

@Component
@RequiredArgsConstructor
public class TotalFilmLikeDaoImpl implements TotalFilmLikeDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TotalLikeFilm> findIsFilmIdAndUserId(Long filmId, Long userId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ONE__TOTAL_FILM_LIKE__FILM_USER.getSql(), filmId, userId);
        if (row.next()) return Optional.of(FactoryModel.buildTotalLikeFilm(row));
        else return Optional.empty();
    }

    @Override
    public List<Film> findPopularIsLimit(Integer limit) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__FILMS_POPULAR__LIMIT.getSql(), limit);

        while (row.next()) {

            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
            if (dirName != null) result.get(filmId).addDirector(Director.builder().id(dirId).name(dirName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findPopularIsLimitAndGenre(Integer limit, Integer searchGenreId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_POPULAR__LIMIT_GENRE.getSql(),
                limit, searchGenreId
        );

        while (row.next()) {

            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
            if (dirName != null) result.get(filmId).addDirector(Director.builder().id(dirId).name(dirName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findPopularIsLimitAndYear(Integer limit, Integer searchYear) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_POPULAR__LIMIT_YEAR.getSql(),
                limit, searchYear
        );
        while (row.next()) {

            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
            if (dirName != null) result.get(filmId).addDirector(Director.builder().id(dirId).name(dirName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findPopularIsLimitAndGenreAndYear(Integer limit, Integer searchGenreId, Integer searchYear) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_POPULAR__SORT_YEAR.getSql(),
                limit, searchGenreId, searchYear
        );
        while (row.next()) {

            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
            if (dirName != null) result.get(filmId).addDirector(Director.builder().id(dirId).name(dirName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<User> findUserToLikeFilm(Long filmId) {
        List<User> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__USERS_TOTAL_FILM_LIKE__FILM.getSql(), filmId);
        while (row.next()) result.add(FactoryModel.buildUser(row));
        return result;
    }

    @Override
    public List<Film> findFilmToLikeUser(Long userId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__FILMS_TOTAL_FILM_LIKE__USER.getSql(), userId);

        while (row.next()) {
            Long filmId = row.getLong("FILM_ID");

            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<TotalLikeFilm> findAll() {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_FILM_LIKE.getSql());
        while (row.next()) result.add(FactoryModel.buildTotalLikeFilm(row));
        return result;
    }

    @Override
    public List<TotalLikeFilm> findAllIsFilmId(Long filmId) {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_FILM_LIKE__FILM.getSql(), filmId);
        while (row.next()) result.add(FactoryModel.buildTotalLikeFilm(row));
        return result;
    }

    @Override
    public List<TotalLikeFilm> findAllIsUserId(Long userId) {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_FILM_LIKE__USER.getSql(), userId);
        while (row.next()) result.add(FactoryModel.buildTotalLikeFilm(row));
        return result;
    }

    @Override
    public List<Film> findCommonFilms(Long firstUserId, Long secondUserId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__COMMON_FILMS.getSql(), firstUserId, secondUserId);

        while (row.next()) {

            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public List<Film> findRecommendationForUser(Long userId) {
        List<Long> friendByFilmsId = findUsersLikeToFilm(userId);
        if (friendByFilmsId.isEmpty()) return Collections.emptyList();
        List<Long> idFilms = getIdFilmsRecommendations(userId, friendByFilmsId);
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__RECOMMENDATION.getSql() + getIdsForSql(idFilms));
        while (row.next()) {
            Long filmId = row.getLong("FILM_ID");
            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");

            if (!result.containsKey(filmId)) result.put(filmId, FactoryModel.buildFilm(row));
            if (genreName != null) result.get(filmId).addGenre(Genre.builder().id(genreId).name(genreName).build());
            if (dirName != null) result.get(filmId).addDirector(Director.builder().id(dirId).name(dirName).build());
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @NotNull
    private static String getIdsForSql(List<Long> idFilms) {
        if (idFilms.isEmpty()) return "()";
        StringBuilder sqlIds = new StringBuilder("(");
        for (Long id : idFilms) {
            sqlIds.append(id).append(",");
        }
        sqlIds.deleteCharAt(sqlIds.length() - 1);
        sqlIds.append(")");
        return sqlIds.toString();
    }

    private List<Long> getIdFilmsRecommendations(Long userId, List<Long> usersLikeToFilm) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                SELECT_FILMS_BY_USERS.getSql() + "WHERE user_id IN " + getIdsForSql(usersLikeToFilm) +
                    " AND film_id NOT IN " + getIdsForSql(findIdFilmsToLikeUser(userId)));
        List<Long> result = new ArrayList<>();
        while (rowSet.next()) {
            result.add(rowSet.getLong("film_id"));
        }
        return result;
    }

    private List<Long> findIdFilmsToLikeUser(Long userId) {
        List<Long> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__FILMS_TOTAL_FILM_LIKE__USER.getSql(), userId);
        while (row.next()) {
            result.add(row.getLong("FILM_ID"));
        }
        return result;
    }


    private List<Long> findUsersLikeToFilm(Long userId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SELECT_USERS_BY_COUNT_FILM_LIKES.getSql(), userId, userId);
        List<Long> result = new ArrayList<>();
        while (rowSet.next()) {
            result.add(rowSet.getLong("user_id"));
        }
        return result;
    }

    @Override
    public void insert(Long filmId, Long userId) {
        jdbcTemplate.update(INSERT_ONE__TOTAL_FILM_LIKE__FILM_USER.getSql(), filmId, userId);
    }

    @Override
    public void update(Long searchFilmId, Long searchUserId, Long filmId, Long userId) {
        jdbcTemplate.update(
                UPDATE_ONE__TOTAL_FILM_LIKE__SET_FILM_USER__FILM_USER.getSql(),
                filmId, userId, searchFilmId, searchUserId
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL__TOTAL_FILM_LIKE.getSql());
    }

    @Override
    public void deleteAll(Long filmId, Long userId) {
        jdbcTemplate.update(DELETE_ONE__TOTAL_FILM_LIKE__FILM_USER.getSql(), filmId, userId);
    }
}