package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalDirectorFilmDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalDirectorFilmDaoImpl implements TotalDirectorFilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TotalDirectorFilm> findAll() {
        Map<Long, TotalDirectorFilm> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "tf.film_id AS filmId, " +
                        "tf.director_id AS directorId, " +
                        "FROM TOTAL_FILM_DIRECTOR AS tf " +
                        "LEFT JOIN DIRECTORS AS d ON tf.director_id = d.id " +
                        "ORDER BY d.id;"
        );
        while (rows.next()) {
            Long totalDirectorFilmId = rows.getLong("ID");
            if (!result.containsKey(totalDirectorFilmId)) {
                TotalDirectorFilm totalDirectorFilm = buildModel(rows);
                result.put(totalDirectorFilmId, totalDirectorFilm);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<TotalDirectorFilm> findById(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM TOTAL_FILM_DIRECTOR WHERE director_id = ?;",
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void insert(Long filmId, Long directorId) {
        jdbcTemplate.update(
                "INSERT INTO TOTAL_FILM_DIRECTOR (film_id, director_id ) " +
                    "VALUES (?, ?);",
                filmId, directorId
        );
    }

    @Override
    public void update(Long filmId, Long directorId) {
        jdbcTemplate.update(
                "UPDATE TOTAL_FILM_DIRECTOR " +
                        "SET film_id = ? " +
                        "WHERE director_id = ?;",
                filmId, directorId
        );
    }

    @Override
    public void delete() {

    }

    @Override
    public void deleteAllByFilmId(Long filmId) {

    }

    @Override
    public void deleteAllByDirectorId(Long directorId) {

    }

    protected TotalDirectorFilm buildModel(@NotNull SqlRowSet row) {
        return TotalDirectorFilm.builder()
                .filmId(row.getLong("ID"))
                .directorId(row.getLong("DIRECTOR_ID"))
                .build();
    }
}
