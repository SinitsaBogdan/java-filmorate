package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.EnumGenreDao;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDaoTest {

    private final FilmDao daoFilm;
    private final TotalGenreFilmDao daoTotalGenreFilm;
    private final TotalFilmLikeDao daoTotalFilmLike;
    private final EnumMpaDaoImpl enumMpaDao;
    private final UserDaoImpl userDao;
    private final EnumGenreDao enumGenreDao;

    @BeforeEach
    public void beforeEach() {

        enumMpaDao.delete();
        daoFilm.delete();
        enumGenreDao.delete();
        daoTotalGenreFilm.delete();
        userDao.delete();
        daoTotalFilmLike.delete();

        enumMpaDao.insert(1, "P", "Описание");
        enumMpaDao.insert(2, "G", "Описание 2");
        enumMpaDao.insert(3, "F", "Описание F");

        daoFilm.insert(1L, 1, "Фильм 1", "", LocalDate.of(2005, 1, 1), 90);
        daoFilm.insert(2L, 2, "Фильм 2", "", LocalDate.of(2004, 1, 1), 110);
        daoFilm.insert(3L, 3, "Фильм 3", "", LocalDate.of(2003, 1, 1), 130);

        enumGenreDao.insert(1, "Комедия");
        enumGenreDao.insert(2, "Ужасы");
        enumGenreDao.insert(3, "Мультики");

        daoTotalGenreFilm.insert(1L, 1);
        daoTotalGenreFilm.insert(2L, 2);
        daoTotalGenreFilm.insert(3L, 3);

        userDao.insert(
                1L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru"
        );
        userDao.insert(
                2L, "Иван", LocalDate.of(1974, 7, 15), "Ivan", "ivan@mail.ru"
        );
        userDao.insert(
                3L, "Ольга", LocalDate.of(1995, 6, 17), "Olga", "olga@email.ru"
        );

        daoTotalFilmLike.insert(1L, 1L);
        daoTotalFilmLike.insert(1L, 2L);
        daoTotalFilmLike.insert(2L, 3L);
    }

    @Test
    @DisplayName("findRows()")
    public void testFindAllRows() {
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 3);
    }

    @Test
    @DisplayName("findRow(filmName)")
    public void testFindRowSearchName() {
        Mpa mpa = Mpa.builder().id(1).name("P").description(null).build();
        Optional<Film> optional = daoFilm.findRow("Фильм 1");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Фильм 1");
        assertEquals(optional.get().getMpa(), mpa);
        assertEquals(optional.get().getDescription(), "");
        assertEquals(optional.get().getReleaseDate(), LocalDate.of(2005, 1, 1));
        assertEquals(optional.get().getDuration(), 90);
    }

    @Test
    @DisplayName("findRow(Long rowId)")
    public void testFindRowSearchId() {
        Mpa mpa = Mpa.builder().id(1).name("P").description(null).build();
        Optional<Film> optional = daoFilm.findRow(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Фильм 1");
        assertEquals(optional.get().getMpa(), mpa);
        assertEquals(optional.get().getDescription(), "");
        assertEquals(optional.get().getReleaseDate(), LocalDate.of(2005, 1, 1));
        assertEquals(optional.get().getDuration(), 90);
    }

    @Test
    @DisplayName("insert(ratingName, name, description, releaseDate, durationMinute)")
    public void testInsertRowRatingDescriptionReleaseDuration() {
        daoFilm.insert(
                100L, 1, "Новый", "Новое", LocalDate.of(1990, 1, 1), 50
        );
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 4);
    }

    @Test
    @DisplayName("insert(rowId, ratingName, name, description, releaseDate, durationMinute)")
    public void testInsertRowAllColumn() {
        Mpa mpa = Mpa.builder().id(1).name("P").description(null).build();
        daoFilm.insert(
                4L, 1, "Новый", "Новое", LocalDate.of(1990, 1, 1), 50
        );
        Optional<Film> optional = daoFilm.findRow("Новый");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Новый");
        assertEquals(optional.get().getMpa(), mpa);
        assertEquals(optional.get().getDescription(), "Новое");
        assertEquals(optional.get().getReleaseDate(), LocalDate.of(1990, 1, 1));
        assertEquals(optional.get().getDuration(), 50);
    }

    @Test
    @DisplayName("update(searchRowId, ratingName, name, description, releaseDate, durationMinute)")
    public void testUpdateRowSearchIdByNameDescriptionReleaseDuration() {
        Mpa mpa = Mpa.builder().id(2).name("G").description(null).build();
        daoFilm.update(
                1L, 2, "Обновленный", "Описание", LocalDate.of(1990, 1, 1), 50
        );
        Optional<Film> optional = daoFilm.findRow("Обновленный");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Обновленный");
        assertEquals(optional.get().getMpa(), mpa);
        assertEquals(optional.get().getDescription(), "Описание");
        assertEquals(optional.get().getReleaseDate(), LocalDate.of(1990, 1, 1));
        assertEquals(optional.get().getDuration(), 50);
    }

    @Test
    @DisplayName("update(searchName, ratingName, name, description, releaseDate, durationMinute)")
    public void testUpdateRowSearchNameByRatingNameDescriptionReleaseDuration() {
        Mpa mpa = Mpa.builder().id(2).name("G").description(null).build();
        daoFilm.update(
                "Фильм 1", 2, "Обновленный", "Описание", LocalDate.of(1990, 1, 1), 50
        );
        Optional<Film> optional = daoFilm.findRow("Обновленный");
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), "Обновленный");
        assertEquals(optional.get().getMpa(), mpa);
        assertEquals(optional.get().getDescription(), "Описание");
        assertEquals(optional.get().getReleaseDate(), LocalDate.of(1990, 1, 1));
        assertEquals(optional.get().getDuration(), 50);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        daoFilm.delete();
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteAllRowsSearchId() {
        daoFilm.delete(1L);
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
        assertTrue(daoFilm.findRow(1L).isEmpty());
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteAllRowsSearchName() {
        daoFilm.delete("Фильм 1");
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
        assertTrue(daoFilm.findRow(1L).isEmpty());
    }

    @Test
    @DisplayName("delete(releaseDate)")
    public void testDeleteAllRowsSearchReleaseDate() {
        daoFilm.delete(LocalDate.of(2003, 1, 1));
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("delete(durationMinute)")
    public void testDeleteAllRowsSearchDurationMinute() {
        daoFilm.delete(110);
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }

    @Test
    @DisplayName("deleteByRating(ratingId)")
    public void testDeleteAllRowsSearchRating() {
        daoFilm.deleteByRating(1);
        Optional<List<Film>> optional = daoFilm.findRows();
        assertTrue(optional.isPresent());
        assertEquals(optional.get().size(), 2);
    }
}