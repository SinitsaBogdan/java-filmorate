package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalFilmLike;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TotalFilmLikeDaoTest {

    private final TotalFilmLikeDao totalFilmLikeDao;
    private final FilmDao filmDao;
    private final UserDao userDao;

    @BeforeEach
    public void beforeEach() {
        totalFilmLikeDao.delete();
        filmDao.delete();
        userDao.delete();

        userDao.insert(
                1L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru"
        );
        userDao.insert(
                2L, "Иван", LocalDate.of(1974, 7, 15), "Ivan", "ivan@mail.ru"
        );
        userDao.insert(
                3L, "Ольга", LocalDate.of(1995, 6, 17), "Olga", "olga@email.ru"
        );

        filmDao.insert(1L, 1, "Фильм 1", "", LocalDate.of(2005, 1, 1), 90);
        filmDao.insert(2L, 2, "Фильм 2", "", LocalDate.of(2004, 1, 1), 110);
        filmDao.insert(3L, 3, "Фильм 3", "", LocalDate.of(2003, 1, 1), 130);

        totalFilmLikeDao.insert(1L, 1L);
        totalFilmLikeDao.insert(1L, 2L);
        totalFilmLikeDao.insert(2L, 2L);
    }

    @Test
    @DisplayName("findPopularFilms(limit)")
    public void testFindLimitPopularFilms() {
        Optional<List<Film>> optional = totalFilmLikeDao.findPopularFilms(2);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findUserToLikeFilm(filmId)")
    public void testFindAllUserToLikeFilmSearchFilmId() {
        Optional<List<User>> optional = totalFilmLikeDao.findUserToLikeFilm(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findFilmToLikeUser(userId)")
    public void testFindAllFilmToLikeUser() {
        Optional<List<Film>> optional = totalFilmLikeDao.findFilmToLikeUser(2L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        Optional<List<TotalFilmLike>> optional = totalFilmLikeDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("findRowsByFilmId(filmId)")
    public void testFindAllRowsByFilmId() {
        Optional<List<TotalFilmLike>> optional = totalFilmLikeDao.findRowsByFilmId(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRowsByUserId(userId)")
    public void testFindAllRowsSearchUserId() {
        Optional<List<TotalFilmLike>> optional = totalFilmLikeDao.findRowsByUserId(2L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("insert(filmId, userId)")
    public void testInsertRowByFilmIdUserId() {
        totalFilmLikeDao.delete(1L, 1L);
        totalFilmLikeDao.insert(1L, 1L);
        Optional<List<TotalFilmLike>> optional = totalFilmLikeDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        totalFilmLikeDao.delete();
        Optional<List<TotalFilmLike>> optional = totalFilmLikeDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 0);
    }

    @Test
    @DisplayName("delete(filmId, userId)")
    public void testDeleteRowSearchFilmIdUserId() {
        totalFilmLikeDao.delete(1L, 2L);
        Optional<List<TotalFilmLike>> optional = totalFilmLikeDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }
}