package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalLikeFilm;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TotalFilmLikeDaoImplTest {

    private final TotalFilmLikeDao totalFilmLikeDao;
    private final TotalGenreFilmDao totalGenreFilmDao;
    private final FilmDao filmDao;
    private final UserDao userDao;

    @BeforeEach
    public void beforeEach() {
        totalFilmLikeDao.deleteAll();
        filmDao.deleteAll();
        userDao.deleteAll();

        userDao.insert(101L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru");
        userDao.insert(102L, "Иван", LocalDate.of(1974, 7, 15), "Ivan", "ivan@mail.ru");
        userDao.insert(103L, "Ольга", LocalDate.of(1995, 6, 17), "Olga", "olga@email.ru");

        filmDao.insert(101L, 1, "Фильм 1", "", LocalDate.of(2005, 1, 1), 90);
        filmDao.insert(102L, 2, "Фильм 2", "", LocalDate.of(2004, 1, 1), 110);
        filmDao.insert(103L, 4, "Фильм 3", "", LocalDate.of(2005, 1, 1), 130);

        totalGenreFilmDao.insert(101L, 2);
        totalGenreFilmDao.insert(102L, 2);
        totalGenreFilmDao.insert(103L, 3);

        totalFilmLikeDao.insert(101L, 101L);
        totalFilmLikeDao.insert(101L, 102L);
        totalFilmLikeDao.insert(102L, 101L);
    }

    @Test
    @DisplayName("findPopularFilms(limit)")
    public void testFindLimitPopularFilms() {
        List<Film> result = totalFilmLikeDao.findPopularIsLimit(2);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findPopularFilms(Integer limit, Integer searchGenreId)")
    public void testFindLimitPopularFilmsByGenre() {
        List<Film> result = totalFilmLikeDao.findPopularIsLimitAndGenre(10, 2);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findPopularFilmsSortByYear(Integer limit, Integer searchYear)")
    public void testFindLimitPopularFilmsByYear() {
        List<Film> result = totalFilmLikeDao.findPopularIsLimitAndYear(5, 2005);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "Фильм 1");
        assertEquals(result.get(1).getName(), "Фильм 3");
    }

    @Test
    @DisplayName("findPopularFilms(Integer limit, Integer searchGenreId, Integer searchYear)")
    public void testFindLimitPopularFilmsSortByYearAndGenreId() {
        List<Film> result = totalFilmLikeDao.findPopularIsLimitAndGenreAndYear(3, 2, 2005);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getName(), "Фильм 1");
    }

    @Test
    @DisplayName("findPopularFilms(Integer limit, Integer searchGenreId, Integer searchYear)")
    public void testFindLimitPopularFilmsSortByIncorrectData() {
        List<Film> result = totalFilmLikeDao.findPopularIsLimitAndGenreAndYear(10, 999, 999);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findUserToLikeFilm(filmId)")
    public void testFindAllUserToLikeFilmSearchFilmId() {
        List<User> result = totalFilmLikeDao.findUserToLikeFilm(1L);
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("findFilmToLikeUser(userId)")
    public void testFindAllFilmToLikeUser() {
        List<Film> result = totalFilmLikeDao.findFilmToLikeUser(2L);
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<TotalLikeFilm> result = totalFilmLikeDao.findAll();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("findRowsByFilmId(filmId)")
    public void testFindAllRowsByFilmId() {
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllIsFilmId(101L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRowsByUserId(userId)")
    public void testFindAllRowsSearchUserId() {
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllIsUserId(102L);
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("insert(filmId, userId)")
    public void testInsertRowByFilmIdUserId() {
        totalFilmLikeDao.deleteAll(101L, 101L);
        totalFilmLikeDao.insert(101L, 101L);
        List<TotalLikeFilm> result = totalFilmLikeDao.findAll();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        totalFilmLikeDao.deleteAll();
        List<TotalLikeFilm> result = totalFilmLikeDao.findAll();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(filmId, userId)")
    public void testDeleteRowSearchFilmIdUserId() {
        totalFilmLikeDao.deleteAll(101L, 101L);
        List<TotalLikeFilm> result = totalFilmLikeDao.findAll();
        assertEquals(result.size(), 2);
    }
}