package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.model.*;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private TotalGenreFilmDao totalGenreFilmDao;

    @Autowired
    private TotalFilmLikeDao totalFilmLikeDao;

    @Autowired
    private UserDao userDao;

    private final Film duplicate = Film.builder()
            .id(1L)
            .name("Фильм 1")
            .description("")
            .releaseDate(LocalDate.parse("2000-01-01"))
            .duration(90)
            .rate(5)
            .build();

    @BeforeEach
    public void beforeEach() {
        totalGenreFilmDao.delete();
        totalFilmLikeDao.delete();
        filmDao.delete();
        userDao.delete();

        filmDao.insert(1L, 1, "Фильм 1", "", LocalDate.parse("2000-01-01"), 90);
        filmDao.insert(2L, 2, "Фильм 2", "", LocalDate.parse("2000-01-01"), 90);
        filmDao.insert(3L, 3, "Фильм 3", "", LocalDate.parse("2000-01-01"), 90);
        filmDao.insert(4L, 4, "Фильм 4", "", LocalDate.parse("2000-01-01"), 90);
        filmDao.insert(5L, 5, "Фильм 5", "", LocalDate.parse("2000-01-01"), 90);

        totalGenreFilmDao.insert(1L, 1);
        totalGenreFilmDao.insert(1L, 2);
        totalGenreFilmDao.insert(2L, 4);

        userDao.insert(1L, "User-1", LocalDate.parse("2000-01-01"), "user-1", "user1@mail.ru");
        userDao.insert(2L, "User-2", LocalDate.parse("2000-01-01"), "user-2", "user2@mail.ru");

        totalFilmLikeDao.insert(1L, 1L);
        totalFilmLikeDao.insert(2L, 1L);
        totalFilmLikeDao.insert(2L, 2L);
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос всех фильмов")
        public void methodGet_AllFilmListTest() throws Exception {
            mockMvc.perform(get("/films"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(5))
            ;
        }

        @Test
        @DisplayName("Запрос фильма: ID -1")
        public void methodGet_FilmIdMinus1Test() throws Exception {
            mockMvc.perform(get("/films/-1"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Запрос фильма: ID 999")
        public void methodGet_FilmId999Test() throws Exception {
            mockMvc.perform(get("/films/-1"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Запрос фильма: ID 1")
        public void methodGet_FilmId1Test() throws Exception {
            mockMvc.perform(get("/films/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Фильм 1"))
                    .andExpect(jsonPath("$.description").value(""))
                    .andExpect(jsonPath("$.genres.length()").value(2))
                    .andExpect(jsonPath("$.releaseDate").value("2000-01-01"))
                    .andExpect(jsonPath("$.duration").value(90))
                    .andExpect(jsonPath("$.mpa.id").value(1))
                    .andExpect(jsonPath("$.mpa.name").value("G"))
            ;
        }

        @Test
        @DisplayName("Запрос списка популярных фильмов: count : default")
        public void methodGet_PopularFilmsLimitDefaultTest() throws Exception {
            mockMvc.perform(get("/films/popular"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(5))
            ;
        }

        @Test
        @DisplayName("Запрос списка популярных фильмов: count : 2")
        public void methodGet_PopularFilmsLimit2Test() throws Exception {
            mockMvc.perform(get("/films/popular?count=2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
            ;
        }

        @Test
        @DisplayName("Запрос списка популярных фильмов: genreId : 0, year : 0")
        public void methodGet_PopularFilmsOverloadTest() throws Exception {
        }

        @Test
        @DisplayName("Запрос списка популярных фильмов: genreId : 1")
        public void methodGet_PopularFilmsGenreId1Test() throws Exception {
        }

        @Test
        @DisplayName("Запрос списка популярных фильмов: year : 2000")
        public void methodGet_PopularFilmsYear2000Test() throws Exception {
        }

        @Test
        @DisplayName("Запрос списка общих фильмов, отсортированные по популярности")
        public void methodGet_CommonFilms() throws Exception {
        }

        @Test
        @DisplayName("Запрос списка фильмов режиссера, отсортированные по популярности")
        public void methodGet_FilmsByDirectorSortedByLikes() throws Exception {
        }

        @Test
        @DisplayName("Запрос списка фильмов режиссера, отсортированные по годам")
        public void methodGet_FilmsByDirectorSortedByYear() throws Exception {
        }

        @Test
        @DisplayName("Запрос списка фильмов по слову")
        public void methodGet_SearchFilmsByTitle() throws Exception {
        }

        @Test
        @DisplayName("Запрос фильма по полному названию")
        public void methodGet_SearchFilmByTitle() throws Exception {
        }

        @Test
        @DisplayName("Запрос фильма по режиссёру")
        public void methodGet_SearchFilmsByDirector() throws Exception {
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата")
        public void methodPost_NewFilmValidTrue_AndDoubleFalseTest() throws Exception {
            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(duplicate))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - name : null")
        public void methodPost_NewFilmValidFalse_NameNullTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name(null)
                    .description("Описание")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .duration(90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - name : empty")
        public void methodPost_NewFilmValidFalse_NameEmptyTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name("")
                    .description("Описание")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .duration(90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : null")
        public void methodPost_NewFilmValidFalse_DescriptionNullTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name("Фильм 10")
                    .description(null)
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .duration(90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : max length")
        public void methodPost_NewFilmValidFalse_DescriptionMaxLengthTest() throws Exception {
            Film film = Film.builder()
                    .id(126L)
                    .name("Фильм 10")
                    .description("Очень длинное название. Очень длинное название. Очень длинное название. Очень длинное название. Очень длинное название. Очень длинное название. Очень длинное название. Очень длинное название. Очень длинное название.")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .duration(90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : null")
        public void methodPost_NewFilmValidFalse_ReleaseDateNullTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name("Фильм 10")
                    .description("Описание")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(null)
                    .duration(90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : min date")
        public void methodPost_NewFilmValidFalse_ReleaseDateMinDataTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name("Фильм 10")
                    .description("Описание")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("1000-01-01"))
                    .duration(90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : null")
        public void methodPost_NewFilmValidFalse_DurationNullTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name("Фильм 10")
                    .description("Описание")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .duration(null)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : min")
        public void methodPost_NewFilmValidFalse_DurationMinTest() throws Exception {
            Film film = Film.builder()
                    .id(125L)
                    .name("Фильм 10")
                    .description("Описание")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .duration(-90)
                    .rate(5)
                    .mpa(Mpa.builder().id(1).build())
                    .build();

            mockMvc.perform(post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление фильма")
        public void methodPut_FilmValidTrueTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;

            mockMvc.perform(get("/films/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("update"))
                    .andExpect(jsonPath("$.description").value("update"))
                    .andExpect(jsonPath("$.genres[0].id").value(1))
                    .andExpect(jsonPath("$.genres[0].name").value("Комедия"))
                    .andExpect(jsonPath("$.releaseDate").value("2010-01-01"))
                    .andExpect(jsonPath("$.duration").value(120))
                    .andExpect(jsonPath("$.mpa.id").value(2))
                    .andExpect(jsonPath("$.mpa.name").value("PG"))
            ;
        }

        @Test
        @DisplayName("Обновление фильма - id : null")
        public void methodPut_FilmValidFalse_IdNullTest() throws Exception {
            Film film = Film.builder()
                    .id(null)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - id : -5")
        public void methodPut_FilmValidFalse_IdNotCorrectTest() throws Exception {
            Film film = Film.builder()
                    .id(-1L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - id : 99")
        public void methodPut_FilmValidFalse_IdNotInCollectionsTest() throws Exception {
            Film film = Film.builder()
                    .id(99L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - name : null")
        public void methodPut_FilmValidFalse_NameNullTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name(null)
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - name : empty")
        public void methodPut_FilmValidFalse_NameEmptyTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - description : null")
        public void methodPut_FilmValidFalse_DescriptionNullTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description(null)
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - description : empty")
        public void methodPut_FilmValidFalse_DescriptionEmptyTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - description : max length")
        public void methodPut_FilmValidFalse_WhitespaceTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание.")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : null")
        public void methodPut_FilmValidFalse_ReleaseDataNullTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(null)
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : min")
        public void methodPut_FilmValidFalse_ReleaseDataMinTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("1010-01-01"))
                    .duration(120)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - duration : null")
        public void methodPut_FilmValidFalse_DurationNullTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(null)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление фильма - duration : min")
        public void methodPut_FilmValidFalse_DurationMinTest() throws Exception {
            Film film = Film.builder()
                    .id(1L)
                    .name("update")
                    .description("update")
                    .genres(Collections.singletonList(Genre.builder().id(1).build()))
                    .releaseDate(LocalDate.parse("2010-01-01"))
                    .duration(-10)
                    .rate(7)
                    .mpa(Mpa.builder().id(2).build())
                    .build();

            mockMvc.perform(put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление не существующего фильма фильма - id : 101")
        public void methodDelete_DeleteFilmById_FailTest() throws Exception {
            mockMvc.perform(delete("/films/101")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Удаление не существующего фильма фильма - id : 101")
        public void methodDelete_DeleteFilmByIdTest() throws Exception {
            mockMvc.perform(delete("/films/1")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/films"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(4))
            ;
        }
    }
}
