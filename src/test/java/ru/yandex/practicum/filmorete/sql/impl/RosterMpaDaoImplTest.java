package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.RosterMpaDao;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RosterMpaDaoImplTest {

    private final RosterMpaDao dao;

    @BeforeEach
    public void beforeEach() {
        dao.delete();
        dao.insert(1, "P", "Описание");
        dao.insert(2, "G", "Описание 2");
    }

    @Test
    @DisplayName("findAllName()")
    public void testFindAllRowsByName() {
        List<String> result = dao.findAllName();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findAllDescription()")
    public void testFindAllRowsByDescription() {
        List<String> result = dao.findAllDescription();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<Mpa> result = dao.findAllMpa();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRow(rowId)")
    public void testFindRowSearchId() {
        Optional<Mpa> optional = dao.findMpa(1);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "P");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("findRow(name)")
    public void testFindRowSearchName() {
        Optional<Mpa> optional = dao.findMpa("P");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "P");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("insert(name, description)")
    public void testInsertRowNameDescription() {
        dao.insert("H", "Описание");
        Optional<Mpa> optional = dao.findMpa("H");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "H");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("insert(rowId, name, descriptions)")
    public void testInsertRowAllColumn() {
        dao.insert(10, "W", "Описание W");
        Optional<Mpa> optional = dao.findMpa(10);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "W");
        assertEquals(optional.get().getDescription(), "Описание W");
    }

    @Test
    @DisplayName("update(searchRowId, name, description)")
    public void testUpdateRowSearchIdByNameDescription() {
        dao.update(2, "F", "Описание F");
        Optional<Mpa> optional = dao.findMpa(2);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "F");
        assertEquals(optional.get().getDescription(), "Описание F");
    }

    @Test
    @DisplayName("update(searchName, name, description)")
    public void testUpdateRowSearchNameByNameDescription() {
        dao.update("P", "H", "Описание");
        Optional<Mpa> optional = dao.findMpa(1);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "H");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.delete();
        List<Mpa> result = dao.findAllMpa();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.delete(2);
        List<Mpa> result = dao.findAllMpa();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteRowSearchName() {
        dao.delete("P");
        List<Mpa> result = dao.findAllMpa();
        assertEquals(result.size(), 1);
    }
}