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
        List<String> result = dao.findAllName();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<Genre> result = dao.findAllGenre();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("findRow(rowId)")
    public void testFindRowSearchId() {
        Optional<Genre> optional = dao.findGenre(100);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Комедия");
    }

    @Test
    @DisplayName("findRow(name)")
    public void testFindRowSearchName() {
        Optional<Genre> optional = dao.findGenre("Комедия");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Комедия");
    }

    @Test
    @DisplayName("insert(name)")
    public void testInsertName() {
        dao.insert("Документальный");
        List<Genre> result = dao.findAllGenre();
        assertEquals(result.size(), 4);
    }

    @Test
    @DisplayName("update(searchRowId, name)")
    public void testUpdateRowSearchIdByName() {
        dao.update(101, "Ужасы");
        Optional<Genre> optional = dao.findGenre(101);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Ужасы");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.delete();
        List<Genre> result = dao.findAllGenre();
        assertEquals(result.size(), 0);

    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.delete(101);
        List<Genre> result = dao.findAllGenre();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteRowSearchName() {
        dao.delete("Комедия");
        List<Genre> result = dao.findAllGenre();
        assertEquals(result.size(), 2);
    }
}