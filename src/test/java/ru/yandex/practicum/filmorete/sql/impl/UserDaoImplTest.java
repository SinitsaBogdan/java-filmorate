package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDaoImplTest {

    private final UserDaoImpl dao;

    @BeforeEach
    public void beforeEach() {
        dao.deleteAll();
        dao.insert(
                100L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru"
        );
        dao.insert(
                101L, "Иван", LocalDate.of(1974, 7, 15), "Ivan", "ivan@mail.ru"
        );
        dao.insert(
                102L, "Ольга", LocalDate.of(1995, 6, 17), "Olga", "olga@email.ru"
        );
    }

    @Test
    @DisplayName("find()")
    void testFindAllRows() {
        List<User> result = dao.findAll();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("find(rowId)")
    void testFindRowById() {
        Optional<User> optional = dao.findById(100L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getId(), 100L);
    }

    @Test
    @DisplayName("find(email)")
    void testFindRowByEmail() {
        Optional<User> optional = dao.findByEmail("maxim@mail.ru");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getEmail(), "maxim@mail.ru");
    }

    @Test
    @DisplayName("insert(name, birthday, login, email)")
    void testInsertByNameBirthdayLoginEmail() {
        dao.insert("Максим2", LocalDate.of(1995, 5, 24), "Maxim2", "maxim2@email.ru");
        Long lastId = dao.findLastIdUser().get();
        Optional<User> optional = dao.findById(lastId);
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("id", lastId)
        );
    }

    @Test
    @DisplayName("insert(rowId, name, birthday, login, email)")
    void testInsertByIdNameBirthdayLoginEmail() {
        dao.insert(103L, "Максим2", LocalDate.of(1995, 5, 24), "Maxim2", "maxim2@email.ru");
        Optional<User> optional = dao.findById(100L);
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("id", 100L)
        );
    }

    @Test
    @DisplayName("update(rowId, name, birthday, login, email)")
    void testUpdate() {
        Optional<User> optional = dao.findById(100L);
        assertThat(optional)
                .isPresent()
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("id", 100L))
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("name", "Максим"))
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("login", "Maxim"))
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("email", "maxim@mail.ru"))
                .hasValueSatisfying(result ->
                        assertThat(result).hasFieldOrPropertyWithValue("birthday",
                                LocalDate.of(1895, 5, 24)));
    }

    @Test
    @DisplayName("delete()")
    void testDeleteAllRows() {
        dao.deleteAll();
        List<User> result = dao.findAll();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    void testDeleteRowById() {
        dao.deleteById(100L);
        List<User> result = dao.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("delete(login)")
    void testDeleteRowByLogin() {
        dao.deleteByLogin("Maxim");
        List<User> result = dao.findAll();
        assertEquals(result.size(), 2);
    }
}