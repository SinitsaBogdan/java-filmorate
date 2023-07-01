package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorete.model.Film;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Film film;

    @BeforeEach
    public void beforeEach() throws Exception {

        film = Film.builder()
            .name("test")
            .description("test")
            .releaseDate(LocalDate.of(2023, 6, 20))
            .duration(135)
            .build();

        mockMvc.perform(
            post("/films")
                .content(objectMapper.writeValueAsString(film))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.releaseDate").value("2023-06-20"))
                .andExpect(jsonPath("$.duration").isNumber())
                .andExpect(jsonPath("$.duration").value(135));
    }

    @AfterEach
    public void afterEach() throws Exception {
        mockMvc.perform(delete("/films"))
                .andExpect(status().isOk());
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос всех фильмов")
        public void methodGet_AllFilmListTest() throws Exception {
            mockMvc.perform(get("/films"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("[{\"id\":1,\"name\":\"test\",\"description\":\"test\",\"releaseDate\":\"2023-06-20\",\"duration\":135}]"));
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата")
        public void methodPost_NewFilmValidTrue_AndDoubleFalseTest() throws Exception {

            film = Film.builder()
                    .name("test")
                    .description("test")
                    .releaseDate(LocalDate.of(2023, 6, 20))
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - name : null")
        public void methodPost_NewFilmValidFalse_NameNullTest() throws Exception {

            film = Film.builder()
                    .description("new")
                    .releaseDate(LocalDate.of(2023, 6, 20))
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_NAME),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - name : empty")
        public void methodPost_NewFilmValidFalse_NameEmptyTest() throws Exception {

            film = Film.builder()
                    .name("")
                    .description("new")
                    .releaseDate(LocalDate.of(2023, 6, 20))
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_NAME),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : null")
        public void methodPost_NewFilmValidFalse_DescriptionNullTest() throws Exception {

            film = Film.builder()
                    .name("new")
                    .releaseDate(LocalDate.of(2023, 6, 20))
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_DESCRIPTION),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : max length")
        public void methodPost_NewFilmValidFalse_DescriptionMaxLengthTest() throws Exception {

            String description = "«Того» - это экранизация реальных событий, произошедших зимой 1925 года, " +
                    "на коварных просторах Аляски, когда поездка из волнующего приключения " +
                    "переросла в настоящее испытание силы, отваги и решимости. " +
                    "Когда смертельная эпидемия дифтерии поразила поселение Ном, " +
                    "а единственное лекарство можно было достать в городе Анкоридже, " +
                    "расположенном в 1000 км, жители обратились " +
                    "к лучшему каюру собачьих упряжек в городе - Леонардо Сеппала (Уилльям Дефо).";

            film = Film.builder()
                    .name("new")
                    .description(description)
                    .releaseDate(LocalDate.of(2023, 6, 20))
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : null")
        public void methodPost_NewFilmValidFalse_ReleaseDateNullTest() throws Exception {

            film = Film.builder()
                    .name("new")
                    .description("new")
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_RELEASED),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : min date")
        public void methodPost_NewFilmValidFalse_ReleaseDateMinDataTest() throws Exception {

            film = Film.builder()
                    .name("new")
                    .description("new")
                    .releaseDate(LocalDate.of(1800, 1, 1))
                    .duration(135)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_RELEASED_MIN),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : null")
        public void methodPost_NewFilmValidFalse_DurationNullTest() throws Exception {

            film = Film.builder()
                    .name("new")
                    .description("new")
                    .releaseDate(LocalDate.of(2023, 1, 1))
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_DURATION),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : min")
        public void methodPost_NewFilmValidFalse_DurationMinTest() throws Exception {

            film = Film.builder()
                    .name("new")
                    .description("new")
                    .releaseDate(LocalDate.of(2023, 1, 1))
                    .duration(-10)
                    .build();

            mockMvc.perform(
                    post("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_DURATION_MIN),
                            Objects.requireNonNull(
                                    result.getResolvedException()).getMessage()
                    ));
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление фильма")
        public void methodPut_FilmValidTrueTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("update"))
                    .andExpect(jsonPath("$.description").value("update"))
                    .andExpect(jsonPath("$.releaseDate").value("2010-04-11"))
                    .andExpect(jsonPath("$.duration").isNumber())
                    .andExpect(jsonPath("$.duration").value(90));
        }

        @Test
        @DisplayName("Обновление фильма - id : null")
        public void methodPut_FilmValidFalse_IdNullTest() throws Exception {

            film = Film.builder()
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_ID),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - id : -5")
        public void methodPut_FilmValidFalse_IdNotCorrectTest() throws Exception {

            film = Film.builder()
                    .id(-5)
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_ID_MIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - id : 99")
        public void methodPut_FilmValidFalse_IdNotInCollectionsTest() throws Exception {

            film = Film.builder()
                    .id(99)
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(500))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("500 INTERNAL_SERVER_ERROR \"%s\"", VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - name : null")
        public void methodPut_FilmValidFalse_NameNullTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_NAME),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - name : empty")
        public void methodPut_FilmValidFalse_NameEmptyTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_NAME),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - description : null")
        public void methodPut_FilmValidFalse_DescriptionNullTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_DESCRIPTION),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - description : empty")
        public void methodPut_FilmValidFalse_DescriptionEmptyTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description("")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_DESCRIPTION),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - description : max length")
        public void methodPut_FilmValidFalse_WhitespaceTest() throws Exception {

            String description = "«Того» - это экранизация реальных событий, произошедших зимой 1925 года, " +
                    "на коварных просторах Аляски, когда поездка из волнующего приключения " +
                    "переросла в настоящее испытание силы, отваги и решимости. " +
                    "Когда смертельная эпидемия дифтерии поразила поселение Ном, " +
                    "а единственное лекарство можно было достать в городе Анкоридже, " +
                    "расположенном в 1000 км, жители обратились " +
                    "к лучшему каюру собачьих упряжек в городе - Леонардо Сеппала (Уилльям Дефо).";

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description(description)
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : null")
        public void methodPut_FilmValidFalse_ReleaseDataNullTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description("update")
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_RELEASED),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : min")
        public void methodPut_FilmValidFalse_ReleaseDataMinTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(1600, 4, 11))
                    .duration(90)
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_RELEASED_MIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - duration : null")
        public void methodPut_FilmValidFalse_DurationNullTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .build();

            mockMvc.perform(
                    put("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_NOT_DURATION),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление фильма - duration : min")
        public void methodPut_FilmValidFalse_DurationMinTest() throws Exception {

            film = Film.builder()
                    .id(1)
                    .name("update")
                    .description("update")
                    .releaseDate(LocalDate.of(2010, 4, 11))
                    .duration(-90)
                    .build();

            mockMvc.perform(
                    put("/films")
                            .content(objectMapper.writeValueAsString(film))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_FILM_DURATION_MIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }
    }
}
