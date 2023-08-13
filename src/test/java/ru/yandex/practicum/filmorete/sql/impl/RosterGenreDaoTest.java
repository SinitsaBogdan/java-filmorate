package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RosterGenreDaoTest {

    private final RosterGenreDao dao;

    @BeforeEach
    public void beforeEach() {
        dao.delete();
        dao.insert(100, "Комедия");
        dao.insert(101, "Ужасы");
        dao.insert(102, "Мультики");
    }

    @Test
    @DisplayName("findAllName()")
    public void testFindAllRowsByName() {
        Optional<List<String>> optional = dao.findAllName();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        Optional<List<Genre>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("findRow(rowId)")
    public void testFindRowSearchId() {
        Optional<Genre> optional = dao.findRow(100);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Комедия");
    }

    @Test
    @DisplayName("findRow(name)")
    public void testFindRowSearchName() {
        Optional<Genre> optional = dao.findRow("Комедия");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Комедия");
    }

    @Test
    @DisplayName("insert(name)")
    public void testInsertName() {
        dao.insert("Документальный");
        Optional<List<Genre>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 4);
    }

    @Test
    @DisplayName("update(searchRowId, name)")
    public void testUpdateRowSearchIdByName() {
        dao.update(101, "Ужасы");
        Optional<Genre> optional = dao.findRow(101);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Ужасы");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.delete();
        Optional<List<Genre>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 0);

    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.delete(101);
        Optional<List<Genre>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteRowSearchName() {
        dao.delete("Комедия");
        Optional<List<Genre>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }
}