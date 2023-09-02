package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.factory.FactoryModel;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalDirectorFilmDao;
import java.util.*;

import static ru.yandex.practicum.filmorete.sql.requests.TotalDirectorFilmRequests.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalDirectorFilmDaoImpl implements TotalDirectorFilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TotalDirectorFilm> findAll() {
        List<TotalDirectorFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(SELECT_ALL__TOTAL_DIRECTOR_FILMS.getSql());
        while (row.next()) result.add(FactoryModel.buildTotalDirectorFilm(row));
        return result;
    }

    @Override
    public List<TotalDirectorFilm> findById(Long directorId) {
        List<TotalDirectorFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT__ONE_TOTAL_DIRECTOR_FILM__DIRECTOR_ID.getSql(),
                directorId
        );
        while (row.next()) result.add(FactoryModel.buildTotalDirectorFilm(row));
        return result;
    }

    @Override
    public List<TotalDirectorFilm> findAllTotalDirectorFilm(Long filmId) {
        List<TotalDirectorFilm> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__TOTAL_DIRECTOR_FILMS__FILM_ID.getSql(),
                filmId
        );
        while (row.next()) result.add(FactoryModel.buildTotalDirectorFilm(row));
        return result;
    }

    @Override
    public void insert(Long filmId, Long directorId) {
        jdbcTemplate.update(
                INSERT_ONE__TOTAL_DIRECTOR_FILM__FILM_ID_DIRECTOR_ID.getSql(),
                filmId, directorId
        );
    }

    @Override
    public void update(Long filmId, Long directorId) {
        jdbcTemplate.update(
                UPDATE_ONE__TOTAL_DIRECTOR_FILM__FILM_ID_DIRECTOR_ID.getSql(),
                filmId, directorId
        );
    }

    @Override
    public void deleteAllByFilmId(Long filmId) {
        jdbcTemplate.update(DELETE_ONE_TOTAL_DIRECTOR_FILM__FILM_ID.getSql(), filmId);
    }

    @Override
    public List<Film> findPopularFilmsByDirector(Long directorId) {
        Set<Long> filmsId = new HashSet<>();
        List<Film> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS__POPULAR_SORT_DIRECTOR.getSql(),
                directorId
        );
        while (row.next()) {

            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");
            Film film = FactoryModel.buildFilm(row);

            if (filmsId.contains(film.getId())) continue;
            if (genreName != null) film.addGenre(Genre.builder().id(genreId).name(genreName).build());
            if (dirName != null) film.addDirector(Director.builder().id(dirId).name(dirName).build());

            result.add(film);
            filmsId.add(film.getId());
        }
        return result;
    }

    @Override
    public List<Film> findFilmsByDirectorSortedByYear(Long directorId) {
        Set<Long> filmsId = new HashSet<>();
        List<Film> result = new ArrayList<>();
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                SELECT_ALL__FILMS_SORT_YEAR_DIRECTOR.getSql(),
                directorId
        );
        while (row.next()) {

            Integer genreId = row.getInt("GENRE_ID");
            String genreName = row.getString("GENRE_NAME");
            Long dirId = row.getLong("DIRECTOR_ID");
            String dirName = row.getString("DIRECTOR_NAME");
            Film film = FactoryModel.buildFilm(row);

            if (filmsId.contains(film.getId())) continue;
            if (genreName != null) film.addGenre(Genre.builder().id(genreId).name(genreName).build());

            film.addDirector(Director.builder().id(dirId).name(dirName).build());
            result.add(film);
            filmsId.add(film.getId());
        }
        return result;
    }

    @Override
    public void delete() {
        jdbcTemplate.update(DELETE_ALL__TOTAL_FILM_DIRECTOR.getSql());
    }
}
