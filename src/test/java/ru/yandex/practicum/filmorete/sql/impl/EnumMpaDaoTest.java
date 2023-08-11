package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.EnumMpaDao;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class EnumMpaDaoTest {

    private final EnumMpaDao dao;

    @BeforeEach
    public void beforeEach(){
        dao.delete();
        dao.insert(1, "P", "Описание");
        dao.insert(2, "G", "Описание 2");
    }

    @Test
    @DisplayName("findAllName()")
    public void testFindAllRowsByName() {
        Optional<List<String>> optional = dao.findAllName();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findAllDescription()")
    public void testFindAllRowsByDescription() {
        Optional<List<String>> optional = dao.findAllDescription();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        Optional<List<Mpa>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRow(rowId)")
    public void testFindRowSearchId() {
        Optional<Mpa> optional = dao.findRow(1);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "P");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("findRow(name)")
    public void testFindRowSearchName() {
        Optional<Mpa> optional = dao.findRow("P");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "P");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("insert(name, description)")
    public void testInsertRowNameDescription() {
        dao.insert("H", "Описание");
        Optional<Mpa> optional = dao.findRow("H");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "H");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("insert(rowId, name, descriptions)")
    public void testInsertRowAllColumn() {
        dao.insert(10, "W", "Описание W");
        Optional<Mpa> optional = dao.findRow(10);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "W");
        assertEquals(optional.get().getDescription(), "Описание W");
    }

    @Test
    @DisplayName("update(searchRowId, name, description)")
    public void testUpdateRowSearchIdByNameDescription() {
        dao.update(2, "F", "Описание F");
        Optional<Mpa> optional = dao.findRow(2);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "F");
        assertEquals(optional.get().getDescription(), "Описание F");
    }

    @Test
    @DisplayName("update(searchName, name, description)")
    public void testUpdateRowSearchNameByNameDescription() {
        dao.update("P", "H", "Описание");
        Optional<Mpa> optional = dao.findRow(1);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "H");
        assertEquals(optional.get().getDescription(), "Описание");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.delete();
        Optional<List<Mpa>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.delete(2);
        Optional<List<Mpa>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 1);
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteRowSearchName() {
        dao.delete("P");
        Optional<List<Mpa>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 1);
    }
}