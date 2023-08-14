package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.TotalUserFriends;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TotalUserFriendsDaoImplTest {

    private final TotalUserFriendsDaoImpl totalUserFriendsDao;

    private final UserDao userDao;

    @BeforeEach
    public void beforeEach() {
        totalUserFriendsDao.delete();
        userDao.delete();
        userDao.insert(
                100L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru"
        );
        userDao.insert(
                101L, "Иван", LocalDate.of(1974, 7, 15), "Ivan", "ivan@mail.ru"
        );
        userDao.insert(
                102L, "Ольга", LocalDate.of(1995, 6, 17), "Olga", "olga@email.ru"
        );
        totalUserFriendsDao.insert(100L, 101L, 2);
        totalUserFriendsDao.insert(100L, 102L, 2);
        totalUserFriendsDao.insert(102L, 101L, 2);
        totalUserFriendsDao.insert(102L, 100L, 2);
        totalUserFriendsDao.insert(101L, 100L, 2);
        totalUserFriendsDao.insert(101L, 102L, 2);
    }

    @Test
    @DisplayName("findFriendsByUser(userId)")
    void testFindAllUserSearchUserId() {
        List<User> result = totalUserFriendsDao.findFriendsByUser(100L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findFriendsCommon(userId, friendId)")
    void testFindAllRowsFriendsCommon() {
        List<User> result = totalUserFriendsDao.findFriendsCommon(102L, 101L);
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("findRows()")
    void testFindAllRows() {
        List<TotalUserFriends> result = totalUserFriendsDao.findRows();
        assertEquals(result.size(), 6);
    }

    @Test
    @DisplayName("findRowsByUserId(userId)")
    void testFindAllRowsSearchUserId() {
        List<TotalUserFriends> result = totalUserFriendsDao.findRowsByUserId(100L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRowsByFriendId(friendId)")
    void testFindAllRowsSearchFriendId() {
        List<TotalUserFriends> result = totalUserFriendsDao.findRowsByFriendId(100L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRowsByStatusId(statusId)")
    void testFindAllRowsSearchStatusId() {
        List<TotalUserFriends> result = totalUserFriendsDao.findRowsByStatusId(2);
        assertEquals(result.size(), 6);
    }

    @Test
    @DisplayName("findRow(userId, friendId)")
    void testFindRowSearchUserIdFriendId() {
        Optional<TotalUserFriends> optional = totalUserFriendsDao.findRow(100L, 101L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getUserId(), 100L);
        assertEquals(optional.get().getFriendId(), 101L);
        assertEquals(optional.get().getStatusId(), 2L);
    }

    @Test
    @DisplayName("insert(userId, friendId, statusId)")
    void testInsertRowAllColumn() {
        userDao.insert(
                103L, "Евгения", LocalDate.of(1995, 6, 17), "Евгения", "евгения@email.ru"
        );
        totalUserFriendsDao.insert(100L, 103L, 1);
        List<TotalUserFriends> result = totalUserFriendsDao.findRows();
        assertEquals(result.size(), 7);
    }

    @Test
    @DisplayName("update(searchUserId, searchFriendId, statusId)")
    void testUpdateRowSearchUserIdFriendIdByStatusId() {
        totalUserFriendsDao.update(100L, 101L, 2);
        Optional<TotalUserFriends> optional = totalUserFriendsDao.findRow(100L, 101L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getStatusId(), 2);
    }

    @Test
    @DisplayName("delete()")
    void testDeleteAllRows() {
        totalUserFriendsDao.delete();
        List<TotalUserFriends> result = totalUserFriendsDao.findRows();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(userId, friendId)")
    void testDeleteRowSearchUserIdFriendId() {
        totalUserFriendsDao.delete(100L, 101L);
        List<TotalUserFriends> result = totalUserFriendsDao.findRows();
        assertEquals(result.size(), 5);
    }

    @Test
    @DisplayName("deleteAllUserId(userId)")
    void testDeleteAllRowsSearchUserId() {
        totalUserFriendsDao.deleteAllUserId(100L);
        List<TotalUserFriends> result = totalUserFriendsDao.findRowsByUserId(100L);
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("deleteAllFriendId(friendId)")
    void testDeleteAllRowsSearchFriendId() {
        totalUserFriendsDao.deleteAllFriendId(100L);
        List<TotalUserFriends> result = totalUserFriendsDao.findRowsByFriendId(100L);
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("deleteAllStatusId(statusId)")
    void testDeleteAllRowsSearchStatusId() {
        totalUserFriendsDao.deleteAllStatusId(1);
        List<TotalUserFriends> result = totalUserFriendsDao.findRows();
        assertEquals(result.size(), 6);
    }
}