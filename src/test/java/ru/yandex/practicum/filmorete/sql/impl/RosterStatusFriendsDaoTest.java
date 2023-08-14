package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.StatusFriends;
import ru.yandex.practicum.filmorete.sql.dao.RosterStatusFriendsDao;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RosterStatusFriendsDaoTest {

    private final RosterStatusFriendsDao dao;

    @BeforeEach
    public void beforeEach() {
        dao.delete();
        dao.insert(1L, "Не подтвержденная");
        dao.insert(2L, "Подтвержденная");

    }

    @Test
    @DisplayName("findAllName()")
    public void testFindAllByName() {
        List<String> result = dao.findAllName();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<StatusFriends> result = dao.findRows();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRow(rowId)")
    public void testFindRowSearchId() {
        Optional<StatusFriends> result = dao.findRow(1L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getStatus(), "Не подтвержденная");
    }

    @Test
    @DisplayName("findRow(name)")
    public void testFindAllRowsSearchStatus() {
        Optional<StatusFriends> result = dao.findRow("Не подтвержденная");
        assertTrue(result.isPresent());
        assertEquals(result.get().getStatus(), "Не подтвержденная");
    }

    @Test
    @DisplayName("insert(status)")
    public void testInsertRowByStatus() {
        dao.insert("Новый");
        List<StatusFriends> result = dao.findRows();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("insert(rowId, status)")
    public void testInsertRowAllColumn() {
        dao.insert(3L, "Новый");
        List<StatusFriends> result = dao.findRows();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("update(searchRowId, status)")
    public void testUpdateRowSearchIdByStatus() {
        dao.update(1L, "Обновленный");
        Optional<StatusFriends> result = dao.findRow(1L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getStatus(), "Обновленный");
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        dao.delete();
        List<StatusFriends> result = dao.findRows();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteRowSearchId() {
        dao.delete(1L);
        List<StatusFriends> result = dao.findRows();
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("delete(status)")
    public void testDeleteAllRowsSearchStatus() {
        dao.delete("Не подтвержденная");
        List<StatusFriends> result = dao.findRows();
        assertEquals(result.size(), 1);
    }
}