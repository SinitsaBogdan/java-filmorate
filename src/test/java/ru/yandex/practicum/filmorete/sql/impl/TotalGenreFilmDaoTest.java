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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TotalGenreFilmDaoTest {

    private final TotalGenreFilmDao totalGenreFilmDao;
    private final FilmDaoImpl filmDao;
    private final EnumMpaDaoImpl enumMpaDao;

    @BeforeEach
    public void beforeEach(){
        totalGenreFilmDao.delete();
        filmDao.delete();
        enumMpaDao.delete();

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
        Optional<List<Genre>> optional = totalGenreFilmDao.findAllRowsSearchFilmIdByGenreId(100L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 4);
    }

    @Test
    @DisplayName("findRowsByFilmId(filmId)")
    public void testFindAllRowsSearchFilmId() {
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRowsByFilmId(100L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("findRowsByGenreId(genreId)")
    public void testFindAllRowsSearchGenreId() {
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRowsByGenreId(2);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 1);
    }

    @Test
    @DisplayName("insert(filmId, genreId)")
    public void testInsertFilmIdAndGenreId() {
        totalGenreFilmDao.insert(100L, 3);
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 5);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        totalGenreFilmDao.delete();
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 0);
    }

    @Test
    @DisplayName("delete(filmId, genreId)")
    public void testDeleteRowSearchFilmIdAndGenreId() {
        totalGenreFilmDao.delete(100L, 1);
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("deleteAllFilmId(filmId)")
    public void testDeleteAllRowsSearchFilmId() {
        totalGenreFilmDao.deleteAllFilmId(100L);
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("deleteAllGenreId(genreId)")
    public void testDeleteAllRowsSearchGenreId() {
        totalGenreFilmDao.deleteAllGenreId(1);
        Optional<List<TotalGenreFilm>> optional = totalGenreFilmDao.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }
}