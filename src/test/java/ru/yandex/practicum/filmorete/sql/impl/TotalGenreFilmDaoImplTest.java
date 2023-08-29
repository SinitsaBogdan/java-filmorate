package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.model.TotalGenreFilm;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TotalGenreFilmDaoImplTest {

    private final TotalGenreFilmDao totalGenreFilmDao;

    private final FilmDaoImpl filmDao;

    private final RosterMpaDaoImpl enumMpaDao;

    private final RosterGenreDaoImpl rosterGenreDao;

    @BeforeEach
    public void beforeEach() {
        totalGenreFilmDao.delete();
        enumMpaDao.delete();
        filmDao.delete();
        rosterGenreDao.delete();

        rosterGenreDao.insert(1, "Комедия");
        rosterGenreDao.insert(2, "Мультики");
        rosterGenreDao.insert(3, "Боевик");

        enumMpaDao.insert(1, "P", "Описание");
        enumMpaDao.insert(2, "G", "Описание 2");
        enumMpaDao.insert(3, "R", "Описание 3");

        filmDao.insert(100L, 1, "Фильм 1", "", LocalDate.of(2005, 1, 1), 90);
        filmDao.insert(101L, 2, "Фильм 2", "", LocalDate.of(2004, 1, 1), 110);
        filmDao.insert(102L, 3, "Фильм 3", "", LocalDate.of(2003, 1, 1), 130);

        totalGenreFilmDao.insert(100L, 2);
        totalGenreFilmDao.insert(100L, 1);
        totalGenreFilmDao.insert(101L, 1);
        totalGenreFilmDao.insert(102L, 3);
    }

    @Test
    @DisplayName("findAllRowsSearchFilmIdByGenreName(filmId)")
    public void testFindAllRowsSearchFilmIdByGenreName() {
        List<Genre> result = totalGenreFilmDao.findAllGenreByFilmId(100L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        List<TotalGenreFilm> result = totalGenreFilmDao.findTotalGenreFilm();
        assertEquals(result.size(), 4);
    }

    @Test
    @DisplayName("findRowsByFilmId(filmId)")
    public void testFindAllRowsSearchFilmId() {
        List<TotalGenreFilm> result = totalGenreFilmDao.findAllTotalGenreFilm(100L);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findRowsByGenreId(genreId)")
    public void testFindAllRowsSearchGenreId() {
        List<TotalGenreFilm> result = totalGenreFilmDao.findAllTotalGenreFilm(2);
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("insert(filmId, genreId)")
    public void testInsertFilmIdAndGenreId() {
        totalGenreFilmDao.insert(100L, 3);
        List<TotalGenreFilm> result = totalGenreFilmDao.findTotalGenreFilm();
        assertEquals(result.size(), 5);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        totalGenreFilmDao.delete();
        List<TotalGenreFilm> result = totalGenreFilmDao.findTotalGenreFilm();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(filmId, genreId)")
    public void testDeleteRowSearchFilmIdAndGenreId() {
        totalGenreFilmDao.delete(100L, 1);
        List<TotalGenreFilm> result = totalGenreFilmDao.findTotalGenreFilm();
        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("deleteAllFilmId(filmId)")
    public void testDeleteAllRowsSearchFilmId() {
        totalGenreFilmDao.deleteAllFilmId(100L);
        List<TotalGenreFilm> result = totalGenreFilmDao.findTotalGenreFilm();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("deleteAllGenreId(genreId)")
    public void testDeleteAllRowsSearchGenreId() {
        totalGenreFilmDao.deleteAllGenreId(1);
        List<TotalGenreFilm> result = totalGenreFilmDao.findTotalGenreFilm();
        assertEquals(result.size(), 2);
    }
}