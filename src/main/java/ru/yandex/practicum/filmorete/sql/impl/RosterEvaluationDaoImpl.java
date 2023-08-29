package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Evaluation;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.sql.dao.RosterEvaluationDao;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RosterEvaluationDaoImpl implements RosterEvaluationDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Evaluation> findAll() {
        Map<Long, Evaluation> result = new HashMap<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "SELECT " +
                        "re.id AS id, " +
                        "re.name AS name, " +
                        "FROM ROSTER_EVALUATION AS re " +
                        "LEFT JOIN REVIEWS AS r ON r.evalutionId = re.id " +
                        "ORDER BY re.id;"
        );
        while (rows.next()) {
            Long evaluationId = rows.getLong("ID");
            if (!result.containsKey(evaluationId)) {
                Evaluation evaluation = buildModel(rows);
                result.put(evaluationId, evaluation);
            }
        }
        if (result.values().isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(result.values());

    }

    @Override
    public Optional<Evaluation> findById(Long rowId) {
        SqlRowSet row = jdbcTemplate.queryForRowSet(
                "SELECT * FROM ROSTER_EVALUATION WHERE id = ?;",
                rowId
        );
        if (row.next()) return Optional.of(buildModel(row));
        else return Optional.empty();
    }

    @Override
    public void insert(Evaluation evaluation) {
        jdbcTemplate.update(
                "INSERT INTO ROSTER_EVALUATION (id, name ) " +
                        "VALUES (?, ?);",
                evaluation.getId(), evaluation.getName()
        );
    }

    @Override
    public void update(Evaluation evaluation) {
        jdbcTemplate.update(
                "UPDATE ROSTER_EVALUATION SET name = ?  WHERE id = ?;",
               evaluation.getName(), evaluation.getId()
        );
    }

    @Override
    public void deleteById(Long rowId) {
        jdbcTemplate.update(
                "DELETE FROM ROSTER_EVALUATION WHERE id = ?;",
                rowId
        );
    }


    protected Evaluation buildModel(@NotNull SqlRowSet row) {
        return Evaluation.builder()
                .id(row.getLong("ID"))
                .name(row.getString("NAME"))
                .build();
    }
}
