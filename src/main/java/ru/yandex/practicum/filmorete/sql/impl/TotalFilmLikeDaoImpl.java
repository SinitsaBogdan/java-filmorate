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

import static ru.yandex.practicum.filmorete.sql.requests.TotalFilmLikeRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalFilmLikeDaoImpl implements TotalFilmLikeDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> findPopularIsLimit(Integer limit) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__FILMS_POPULAR__LIMIT.getSql(), limit);
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
    public List<Film> findPopularIsLimitAndGenre(Integer limit, Integer searchGenreId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_POPULAR__LIMIT_GENRE.getSql(),
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
    public List<Film> findPopularIsLimitAndYear(Integer limit, Integer searchYear) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_POPULAR__LIMIT_YEAR.getSql(),
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
    public List<Film> findPopularIsLimitAndGenreAndYear(Integer limit, Integer searchGenreId, Integer searchYear) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_POPULAR__SORT_YEAR.getSql(),
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
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__USERS_TOTAL_FILM_LIKE__FILM.getSql(), filmId);
        while (rows.next()) result.add(FactoryModel.buildUser(rows));
        return result;
    }

    @Override
    public List<Film> findFilmToLikeUser(Long userId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__FILMS_TOTAL_FILM_LIKE__USER.getSql(), userId);
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
    public List<TotalLikeFilm> findAll() {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_FILM_LIKE.getSql());
        while (rows.next()) result.add(FactoryModel.buildTotalLikeFilm(rows));
        return result;
    }

    @Override
    public List<TotalLikeFilm> findAllIsFilmId(Long filmId) {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_FILM_LIKE__FILM.getSql(), filmId);
        while (rows.next()) result.add(FactoryModel.buildTotalLikeFilm(rows));
        return result;
    }

    @Override
    public List<TotalLikeFilm> findAllIsUserId(Long userId) {
        List<TotalLikeFilm> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_FILM_LIKE__USER.getSql(), userId);
        while (rows.next()) result.add(FactoryModel.buildTotalLikeFilm(rows));
        return result;
    }

    @Override
    public List<Film> findCommonFilms(Long firstUserId, Long secondUserId) {
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__COMMON_FILMS.getSql(), firstUserId, secondUserId);

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
    public List<Film> findRecommendationForUser(Long userId) {
        Optional<Long> friendByFilmsId = findFriendByFilmsId(userId);
        if (friendByFilmsId.isEmpty()) return Collections.emptyList();
        Map<Long, Film> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__RECOMMENDATION.getSql(), friendByFilmsId.get(), userId);

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
        SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_ALL__USERS_FILMS.getSql());
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