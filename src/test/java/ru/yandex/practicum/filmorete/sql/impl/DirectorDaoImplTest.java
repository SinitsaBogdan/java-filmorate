package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DirectorDaoImplTest {
    private final DirectorDao dao;

    @BeforeEach
    public void beforeEach() {
        dao.deleteAll();
        dao.insert(10L, "director-1");
        dao.insert(20L, "director-2");
        dao.insert(30L, "director-3");
    }

    @Test
    @DisplayName("findAll()")
    public void testFindAllRows() {
        List<Director> result = dao.findAll();
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getName(), "director-1");
    }

    @Test
    @DisplayName("findById()")
    public void testFindRowSearchId() {
        Optional<Director> optional = dao.findById(20L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "director-2");
    }

    @Test
    @DisplayName("insert(name)")
    public void testInsertName() {
        dao.insert("director-4");
        List<Director> result = dao.findAll();
        assertEquals(result.size(), 4);
    }

    @Test
    @DisplayName("insert")
    public void testInsert() {
        dao.insert(4L, "director-4");
        List<Director> result = dao.findAll();
        assertEquals(result.size(), 4);
    }

    @Test
    @DisplayName("update(Long id, String name)")
    public void testUpdateRowSearchIdByName() {
        dao.update(10L, "updateDirector-1");
        Optional<Director> optional = dao.findById(10L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "updateDirector-1");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.deleteAll();
        List<Director> result = dao.findAll();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.deleteById(10L);
        List<Director> result = dao.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteRowSearchName() {
        dao.deleteByName("director-1");
        List<Director> result = dao.findAll();
        assertEquals(result.size(), 2);
    }
}