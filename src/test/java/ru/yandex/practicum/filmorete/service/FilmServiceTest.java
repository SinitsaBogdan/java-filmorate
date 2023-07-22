package ru.yandex.practicum.filmorete.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.FilmStorage;
import ru.yandex.practicum.filmorete.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmServiceTest {

    @Autowired
    private FilmStorage filmStorage;

    @Autowired
    private UserStorage userStorage;

    @Autowired
    private FilmService service;

    static User user1;

    static Film film;

    @BeforeAll
    static void beforeAll() {
        film = Film.builder()
                .name("Один дома")
                .duration(90)
                .releaseDate(LocalDate.of(2000, 10, 1))
                .description("Описание")
                .build();

        user1 = User.builder()
                .name("Богдан")
                .login("Sinitsa")
                .birthday(LocalDate.of(1997, 4, 11))
                .email("a@a.com")
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        filmStorage.addFilm(film);
        userStorage.addUser(user1);
        service.addLike(film.getId(), user1.getId());
    }

    @AfterEach
    public void afterEach() {
        filmStorage.clear();
        userStorage.clear();
    }

    @Test
    @DisplayName("getFilmsLikesToUserTest")
    public void getFilmsLikesToUserTest() {
        System.out.println(service.getFilmsLikesToUser(user1));
        assertEquals(film, service.getFilmsLikesToUser(user1).get(0));
    }

    @Test
    @DisplayName("getUserLikesToFilmTest")
    public void getUserLikesToFilmTest() {
        assertEquals(user1, service.getUserLikesToFilm(film).get(0));
    }

    @Test
    @DisplayName("removeLikeTest")
    public void removeLikeTest() {
        System.out.println(film.getLikeUsers());
        service.removeLike(film.getId(), user1.getId());
        System.out.println(film.getLikeUsers());

        assertTrue(service.getUserLikesToFilm(film).isEmpty());
        System.out.println(service.getFilmsLikesToUser(user1));
        assertTrue(service.getFilmsLikesToUser(user1).isEmpty());
    }

    @Test
    @DisplayName("getPopularFilmsTest")
    public void getPopularFilmsTest() {
        filmStorage.addFilm(Film.builder()
                .name("Один дома 2")
                .duration(135)
                .releaseDate(LocalDate.of(2005, 10, 1))
                .description("Описание")
                .build()
        );

        Film film2 = Film.builder()
                .name("Один дома 3")
                .duration(135)
                .releaseDate(LocalDate.of(2007, 10, 1))
                .description("Описание")
                .build();

        filmStorage.addFilm(film2);
        service.addLike(film2.getId(), user1.getId());
        List<Film> result = service.getPopularFilms(2);

        assertEquals(2, result.size());
        assertEquals(film, result.get(0));
        assertEquals(film2, result.get(1));
    }
}