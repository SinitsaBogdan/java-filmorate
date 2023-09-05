package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
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
        Optional<Long> friendByFilmsId = findUserLikeToFilm(userId);
        if (friendByFilmsId.isEmpty()) return Collections.emptyList();
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__RECOMMENDATION.getSql(), friendByFilmsId.get(), userId);

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

    private Optional<Long> findUserLikeToFilm(Long userId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_USER_BY_COUNT_FILM_LIKES.getSql(), userId, userId);
        if (!row.next()) return Optional.empty();
        return row.getLong("common_likes") != 0 ? Optional.of(row.getLong("user_id")) : Optional.empty();
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