package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.TypeReview;
import ru.yandex.practicum.filmorete.sql.dao.RosterTypeReviewDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RosterTypeReviewDaoImpl implements RosterTypeReviewDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TypeReview> findAll() {
        Map<Long, TypeReview> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "tr.id AS id, " +
                        "tr.name AS name, " +
                        "FROM ROSTER_TYPE_REVIEW AS rt " +
                        "ORDER BY tr.id;"
        );
        while (rows.next()) {
            Long typeReviewId = rows.getLong("ID");
            if (!result.containsKey(typeReviewId)) {
                TypeReview typeReview = buildModel(rows);
                result.put(typeReviewId, typeReview);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());
    }

    @Override
    public Optional<TypeReview> findById(Long rowId) {

        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_TYPE_REVIEW WHERE id = ?;",
                rowId
        );
        if (row.next()) {
            return Optional.of(buildModel(row));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void insert(TypeReview typeReview) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_TYPE_REVIEW (id, name ) " +
                        "VALUES (?, ?);",
                typeReview.getId(), typeReview.getName()
        );
    }

    @Override
    public void update(TypeReview typeReview) {
        jdbcTemplate.update(
                "UPDATE ROSTER_TYPE_REVIEW SET name = ?  WHERE id = ?;",
                typeReview.getId(), typeReview.getName()
        );
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_TYPE_REVIEW WHERE id = ?;",
                rowId
        );
    }

    protected TypeReview buildModel(@NotNull SqlRowSet row) {
        return TypeReview.builder()
                .id(row.getInt("ID"))
                .name(row.getString("NAME"))
                .build();
    }
}
