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
    private final TotalGenreFilmDao genreFilmDao;
    private final FilmDao filmDao;
    private final UserDao userDao;

    @BeforeEach
    public void beforeEach() {
        totalFilmLikeDao.delete();
        filmDao.deleteAll();
        userDao.delete();
        genreFilmDao.delete();

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
        filmDao.insert(3L, 3, "Фильм 3", "", LocalDate.of(2005, 1, 1), 130);

        totalFilmLikeDao.insert(1L, 1L);
        totalFilmLikeDao.insert(1L, 2L);
        totalFilmLikeDao.insert(2L, 2L);
    }

    @Test
    @DisplayName("findPopularFilms(limit)")
    public void testFindLimitPopularFilms() {
        List<Film> result = totalFilmLikeDao.findPopularFilms(2);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findPopularFilms(Integer limit, Integer searchGenreId)")
    public void testFindLimitPopularFilmsByGenre() {
        genreFilmDao.insert(1L, 4);
        genreFilmDao.insert(2L, 5);
        genreFilmDao.insert(3L, 5);
        List<Film> result = totalFilmLikeDao.findPopularFilms(10, 5);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "Фильм 2");
    }

    @Test
    @DisplayName("findPopularFilmsSortByYear(Integer limit, Integer searchYear)")
    public void testFindLimitPopularFilmsByYear() {
        List<Film> result = totalFilmLikeDao.findPopularFilmsSortByYear(5, 2005);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "Фильм 1");
        assertEquals(result.get(1).getName(), "Фильм 3");
    }

    @Test
    @DisplayName("findPopularFilms(Integer limit, Integer searchGenreId, Integer searchYear)")
    public void testFindLimitPopularFilmsSortByYearAndGenreId() {
        genreFilmDao.insert(1L, 5);
        genreFilmDao.insert(2L, 5);
        genreFilmDao.insert(3L, 5);
        List<Film> result = totalFilmLikeDao.findPopularFilms(3, 5, 2005);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "Фильм 1");
        assertEquals(result.get(1).getName(), "Фильм 3");
    }

    @Test
    @DisplayName("findPopularFilms(Integer limit, Integer searchGenreId, Integer searchYear)")
    public void testFindLimitPopularFilmsSortByIncorrectData() {
        List<Film> result = totalFilmLikeDao.findPopularFilms(10, 999, 999);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findUserToLikeFilm(filmId)")
    public void testFindAllUserToLikeFilmSearchFilmId() {
        List<User> result = totalFilmLikeDao.findUserToLikeFilm(1L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findFilmToLikeUser(userId)")
    public void testFindAllFilmToLikeUser() {
        List<Film> result = totalFilmLikeDao.findFilmToLikeUser(2L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllTotalFilmLike();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("findRowsByFilmId(filmId)")
    public void testFindAllRowsByFilmId() {
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllTotalFilmLikeByFilmId(1L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRowsByUserId(userId)")
    public void testFindAllRowsSearchUserId() {
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllTotalFilmLikeByUserId(2L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("insert(filmId, userId)")
    public void testInsertRowByFilmIdUserId() {
        totalFilmLikeDao.delete(1L, 1L);
        totalFilmLikeDao.insert(1L, 1L);
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllTotalFilmLike();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        totalFilmLikeDao.delete();
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllTotalFilmLike();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(filmId, userId)")
    public void testDeleteRowSearchFilmIdUserId() {
        totalFilmLikeDao.delete(1L, 2L);
        List<TotalLikeFilm> result = totalFilmLikeDao.findAllTotalFilmLike();
        assertEquals(result.size(), 2);
    }
}