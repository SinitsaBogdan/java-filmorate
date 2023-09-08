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
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDaoImplTest {

    private final FilmDao daoFilm;
    private final TotalGenreFilmDao daoTotalGenreFilm;
    private final TotalFilmLikeDao daoTotalFilmLike;
    private final RosterMpaDao enumMpaDao;
    private final UserDao userDao;
    private final RosterGenreDao enumGenreDao;
    private final TotalDirectorFilmDao daoTotalDirectorFilm;
    private final DirectorDao directorDao;

    @BeforeEach
    public void beforeEach() {

        enumMpaDao.delete();
        daoFilm.deleteAll();
        enumGenreDao.deleteAll();
        daoTotalGenreFilm.delete();
        userDao.deleteAll();
        daoTotalFilmLike.deleteAll();
        directorDao.deleteAll();
        daoTotalDirectorFilm.delete();

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
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 3);
    }


    @Test
    @DisplayName("findRow(Long rowId)")
    public void testFindRowSearchId() {
        Mpa mpa = Mpa.builder().id(1).name("P").description(null).build();
        Optional<Film> optional = daoFilm.findFilmById(1L);
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
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 4);
    }

    @Test
    @DisplayName("delete()")
    public void testDeleteAllRows() {
        daoFilm.deleteAll();
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("delete(rowId)")
    public void testDeleteAllRowsSearchId() {
        daoFilm.deleteByFilmId(1L);
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 2);
        assertTrue(daoFilm.findFilmById(1L).isEmpty());
    }

    @Test
    @DisplayName("delete(name)")
    public void testDeleteAllRowsSearchName() {
        daoFilm.deleteAllByFilmName("Фильм 1");
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 2);
        assertTrue(daoFilm.findFilmById(1L).isEmpty());
    }

    @Test
    @DisplayName("delete(releaseDate)")
    public void testDeleteAllRowsSearchReleaseDate() {
        daoFilm.deleteAllByReleaseDate(LocalDate.of(2003, 1, 1));
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("delete(durationMinute)")
    public void testDeleteAllRowsSearchDurationMinute() {
        daoFilm.deleteAllIsDuration(110);
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("deleteByRating(ratingId)")
    public void testDeleteAllRowsSearchRating() {
        daoFilm.deleteAllByMpaId(1);
        List<Film> result = daoFilm.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("getFilmsBySearchParam(query,title)")
    public void testGetFilmsBySearchParamTitle() {
        List<Film> searchParam = daoFilm.findAll("Фильм 1", Collections.singletonList("title"));
        Optional<Film> optional = daoFilm.findFilmById(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), searchParam.get(0).getName());
        assertEquals(optional.get().getMpa(), searchParam.get(0).getMpa());
        assertEquals(optional.get().getDescription(), searchParam.get(0).getDescription());
        assertEquals(optional.get().getReleaseDate(), searchParam.get(0).getReleaseDate());
        assertEquals(optional.get().getDuration(), searchParam.get(0).getDuration());
    }

    @Test
    @DisplayName("getFilmsBySearchParam(query,director)")
    public void testGetFilmsBySearchParamDirector() {
        directorDao.insert("Director-1");
        daoTotalDirectorFilm.insert(1L, 2L);

        List<Film> searchParam = daoFilm.findAll("Director", Collections.singletonList("director"));
        Optional<Film> optional = daoFilm.findFilmById(1L);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getName(), searchParam.get(0).getName());
        assertEquals(optional.get().getMpa(), searchParam.get(0).getMpa());
        assertEquals(optional.get().getDescription(), searchParam.get(0).getDescription());
        assertEquals(optional.get().getReleaseDate(), searchParam.get(0).getReleaseDate());
        assertEquals(optional.get().getDuration(), searchParam.get(0).getDuration());
    }

    @Test
    @DisplayName("getFilmsBySearchParam(query,title&director)")
    public void testGetFilmsBySearchParamTitleAndDirector() {
        directorDao.insert("Director-1");
        daoTotalDirectorFilm.insert(2L, 1L);

        List<Film> searchParam = daoFilm.findAll("1", List.of("title", "director"));
        assertEquals(2, searchParam.size());
    }
}