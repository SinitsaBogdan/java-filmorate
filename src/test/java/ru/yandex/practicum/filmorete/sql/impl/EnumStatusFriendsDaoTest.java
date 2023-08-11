package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.StatusFriends;
import ru.yandex.practicum.filmorete.sql.dao.EnumStatusFriendsDao;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class EnumStatusFriendsDaoTest {

    private final EnumStatusFriendsDao dao;

    @BeforeEach
    public void beforeEach() {
        dao.delete();
        dao.insert(1L, "Не подтвержденная");
        dao.insert(2L, "Подтвержденная");

    }

    @Test
    @DisplayName("findAllName()")
    public void testFindAllByName() {
        Optional<List<String>> optional = dao.findAllName();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        Optional<List<StatusFriends>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRow(rowId)")
    public void testFindRowSearchId() {
        Optional<StatusFriends> optional = dao.findRow(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getStatus(), "Не подтвержденная");
    }

    @Test
    @DisplayName("findRow(name)")
    public void testFindAllRowsSearchStatus() {
        Optional<StatusFriends> optional = dao.findRow("Не подтвержденная");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getStatus(), "Не подтвержденная");
    }

    @Test
    @DisplayName("insert(status)")
    public void testInsertRowByStatus() {
        dao.insert("Новый");
        Optional<List<StatusFriends>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("insert(rowId, status)")
    public void testInsertRowAllColumn() {
        dao.insert(3L, "Новый");
        Optional<List<StatusFriends>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("update(searchRowId, status)")
    public void testUpdateRowSearchIdByStatus() {
        dao.update(1L, "Обновленный");
        Optional<StatusFriends> optional = dao.findRow(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getStatus(), "Обновленный");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.delete();
        Optional<List<StatusFriends>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.delete(1L);
        Optional<List<StatusFriends>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 1);
    }

    @Test
    @DisplayName("delete(status)")
    public void testDeleteAllRowsSearchStatus() {
        dao.delete("Не подтвержденная");
        Optional<List<StatusFriends>> optional = dao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 1);
    }
}