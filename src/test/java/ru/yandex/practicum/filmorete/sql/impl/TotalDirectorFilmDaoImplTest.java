package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.TotalDirectorFilm;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalDirectorFilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TotalDirectorFilmDaoImplTest {
    private final TotalDirectorFilmDao directorFilmDao;
    private final DirectorDao directorDao;
    private final FilmDaoImpl filmDao;
    private final RosterMpaDaoImpl enumMpaDao;
    private final TotalFilmLikeDao likeDao;
    private final UserDaoImpl userDao;

    @BeforeEach
    public void beforeEach() {
        directorFilmDao.delete();
        directorDao.delete();
        filmDao.deleteAll();
        enumMpaDao.delete();
        userDao.deleteAll();

        directorDao.insert(10L, "director-1");
        directorDao.insert(20L, "director-2");
        directorDao.insert(30L, "director-3");

        enumMpaDao.insert(1, "P", "Описание");
        enumMpaDao.insert(2, "G", "Описание 2");
        enumMpaDao.insert(3, "R", "Описание 3");

        filmDao.insert(100L, 1, "Фильм 1", "", LocalDate.of(2005, 1, 1), 90);
        filmDao.insert(101L, 2, "Фильм 2", "", LocalDate.of(2004, 1, 1), 110);
        filmDao.insert(102L, 3, "Фильм 3", "", LocalDate.of(2003, 1, 1), 130);

        directorFilmDao.insert(100L, 10L);
        directorFilmDao.insert(100L, 20L);
        directorFilmDao.insert(101L, 20L);
        directorFilmDao.insert(102L, 30L);
    }

    @Test
    @DisplayName("findAll()")
    public void testFindAllRows() {
        List<TotalDirectorFilm> result = directorFilmDao.findAll();
        assertEquals(result.size(), 4);
    }

    @Test
    @DisplayName("findAllTotalDirectorFilm(Long filmId)")
    public void testFindAllRowsSearchFilmId() {
        List<TotalDirectorFilm> result = directorFilmDao.findAllTotalDirectorFilm(100L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findById(Long directorId)")
    public void testFindAllRowsSearchDirectorId() {
        List<TotalDirectorFilm> result = directorFilmDao.findById(20L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("insert(Long filmId, Long directorId)")
    public void testInsertFilmIdAndDirectorId() {
        directorFilmDao.insert(102L, 10L);
        List<TotalDirectorFilm> result = directorFilmDao.findAll();
        assertEquals(result.size(), 5);
    }

    @Test
    @DisplayName("update(Long filmId, Long directorId)")
    public void testUpdateFilmIdAndDirectorId() {
        directorFilmDao.update(101L, 30L);
        List<TotalDirectorFilm> resultAll = directorFilmDao.findAll();
        assertEquals(resultAll.size(), 4);
        List<TotalDirectorFilm> result = directorFilmDao.findAllTotalDirectorFilm(101L);
        assertEquals(result.get(1).getDirectorId(), 30L);
    }

    @Test
    @DisplayName("deleteAllByFilmId(Long filmId)")
    public void testDeleteAllRowsSearchFilmId() {
        directorFilmDao.deleteAllByFilmId(100L);
        List<TotalDirectorFilm> result = directorFilmDao.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        directorFilmDao.delete();
        List<TotalDirectorFilm> result = directorFilmDao.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findPopularFilmsByDirector(Long directorId)")
    public void testGetPopularFilmsSearchDirectorID() {
        userDao.insert(100L, "user1", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru");
        userDao.insert(200L, "user2", LocalDate.of(1995, 5, 24), "Stan", "stan@mail.ru");
        userDao.insert(300L, "user3", LocalDate.of(2005, 5, 24), "Kyle", "kyle@mail.ru");
        directorFilmDao.insert(101L, 10L);
        directorFilmDao.insert(102L, 10L);
        likeDao.insert(102L, 100L);
        likeDao.insert(102L, 200L);
        likeDao.insert(102L, 300L);
        likeDao.insert(100L, 200L);
        likeDao.insert(100L, 100L);
        List<Film> result = directorFilmDao.findPopularFilmsByDirector(10L);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getId(), 102L);
        assertEquals(result.get(1).getId(), 100L);
        assertEquals(result.get(2).getId(), 101L);
    }

    @Test
    @DisplayName("findFilmsByDirectorSortedByYear(Long directorId)")
    public void testGetFilmsSearchDirectorIDSortedByYear() {
        directorFilmDao.insert(101L, 10L);
        directorFilmDao.insert(102L, 10L);
        List<Film> result = directorFilmDao.findFilmsByDirectorSortedByYear(10L);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getId(), 102L);
        assertEquals(result.get(1).getId(), 101L);
        assertEquals(result.get(2).getId(), 100L);
    }
}