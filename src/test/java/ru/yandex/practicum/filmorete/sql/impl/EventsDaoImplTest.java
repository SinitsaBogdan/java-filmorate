package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventsDaoImplTest {

    private final EventsDao eventsDao;
    private final UserDao userDao;

    @BeforeEach
    public void beforeEach() {

        eventsDao.deleteAll();
        userDao.deleteAll();

        userDao.insert(
                1L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru"
        );
        userDao.insert(
                2L, "Иван", LocalDate.of(1974, 7, 15), "Ivan", "ivan@mail.ru"
        );
        userDao.insert(
                3L, "Ольга", LocalDate.of(1995, 6, 17), "Olga", "olga@email.ru"
        );

        eventsDao.insert(1L, EventType.REVIEW, EventOperation.UPDATE, 1L, 21L);
        eventsDao.insert(2L, EventType.FRIEND, EventOperation.ADD, 1L, 22L);
        eventsDao.insert(3L, EventType.LIKE, EventOperation.REMOVE, 2L, 23L);
        eventsDao.insert(4L, EventType.LIKE, EventOperation.ADD, 3L, 24L);
        eventsDao.insert(5L, EventType.LIKE, EventOperation.UPDATE, 3L, 25L);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 5);
    }

    @Test
    @DisplayName("findRow(userId)")
    public void testFindAllByUserId() {
        List<Event> result = eventsDao.findById(3L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRow(eventId)")
    public void testFindByEventId() {
        Optional<Event> optional = eventsDao.findOneByEventId(1L);
        assertEquals(optional.get().getEventId(), 1L);
    }

    @Test
    @DisplayName("insert(id, type, operation, userId, entityId)")
    public void testInsert() {
        eventsDao.insert(6L, EventType.LIKE, EventOperation.ADD, 2L, 12L);
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 6);
    }

    @Test
    @DisplayName("update(id, type, operation, userId, entityId)")
    public void testUpdate() {
        eventsDao.update(5L, EventType.FRIEND, EventOperation.UPDATE, 3L, 25L);
        Optional<Event> optional = eventsDao.findOneByEventId(5L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getEventId(), 5L);
        assertEquals(optional.get().getEventType(), EventType.FRIEND);
        assertEquals(optional.get().getOperation(), EventOperation.UPDATE);
        assertEquals(optional.get().getUserId(), 3L);
        assertEquals(optional.get().getEntityId(), 25L);
    }

    @Test
    @DisplayName("delete()")
    public void testDelete() {
        eventsDao.deleteAll();
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("deleteByEventId(rowId)")
    public void testDeleteByEventId() {
        eventsDao.deleteAllIsEventId(1L);
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 4);
        assertTrue(eventsDao.findOneByEventId(1L).isEmpty());
    }

    @Test
    @DisplayName("deleteAll(eventType)")
    public void testDeleteAll() {
        eventsDao.deleteByEventType(EventType.FRIEND);
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 4);
        assertTrue(eventsDao.findOneByEventId(2L).isEmpty());
    }

    @Test
    @DisplayName("deleteAll(operation)")
    public void testDeleteAllByOperation() {
        eventsDao.deleteAllByEventOperation(EventOperation.REMOVE);
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 4);
        assertTrue(eventsDao.findOneByEventId(3L).isEmpty());
    }

    @Test
    @DisplayName("deleteAll(userId)")
    public void testDeleteAllByUserId() {
        eventsDao.deleteAllIsUserId(2L);
        List<Event> result = eventsDao.findAll();
        assertEquals(result.size(), 4);
        assertTrue(eventsDao.findById(2L).isEmpty());
    }
}
