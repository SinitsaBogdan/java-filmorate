package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() throws Exception {

        mockMvc.perform(
            post("/films")
                .content(
                        "{" +
                        "\"name\":\"test\"," +
                        "\"description\":\"test\"," +
                        "\"releaseDate\":\"2023-01-01\"," +
                        "\"duration\":135" +
                        "}"
                )
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.releaseDate").value("2023-01-01"))
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
                    .andExpect(content().string("[{\"id\":1,\"name\":\"test\",\"description\":\"test\",\"releaseDate\":\"2023-01-01\",\"duration\":135}]"));
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата")
        public void methodPost_NewFilmValidTrue_AndDoubleFalseTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - name : null")
        public void methodPost_NewFilmValidFalse_NameNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - name : empty")
        public void methodPost_NewFilmValidFalse_NameEmptyTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"\"," +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : null")
        public void methodPost_NewFilmValidFalse_DescriptionNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DESCRIPTION.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : max length")
        public void methodPost_NewFilmValidFalse_DescriptionMaxLengthTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"«Того» - это экранизация реальных событий, произошедших зимой 1925 года, \" +\n" +
                                                    "\"на коварных просторах Аляски, когда поездка из волнующего приключения \" +\n" +
                                                    "\"переросла в настоящее испытание силы, отваги и решимости. \" +\n" +
                                                    "\"Когда смертельная эпидемия дифтерии поразила поселение Ном, \" +\n" +
                                                    "\"а единственное лекарство можно было достать в городе Анкоридже, \" +\n" +
                                                    "\"расположенном в 1000 км, жители обратились \" +\n" +
                                                    "\"к лучшему каюру собачьих упряжек в городе - Леонардо Сеппала (Уилльям Дефо).\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : null")
        public void methodPost_NewFilmValidFalse_ReleaseDateNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"test\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_RELEASED.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : min date")
        public void methodPost_NewFilmValidFalse_ReleaseDateMinDataTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"1500-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_RELEASED_MIN.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : null")
        public void methodPost_NewFilmValidFalse_DurationNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DURATION.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : min")
        public void methodPost_NewFilmValidFalse_DurationMinTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":-90" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DURATION_MIN.toString());
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление фильма")
        public void methodPut_FilmValidTrueTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"description\":\"update\"," +
                                "\"releaseDate\":\"2010-01-01\"," +
                                "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("update"))
                    .andExpect(jsonPath("$.description").value("update"))
                    .andExpect(jsonPath("$.releaseDate").value("2010-01-01"))
                    .andExpect(jsonPath("$.duration").isNumber())
                    .andExpect(jsonPath("$.duration").value(90));
        }

        @Test
        @DisplayName("Обновление фильма - id : null")
        public void methodPut_FilmValidFalse_IdNullTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                            .content(
                                    "{" +
                                    "\"name\":\"test\"," +
                                    "\"description\":\"test\"," +
                                    "\"releaseDate\":\"2023-01-01\"," +
                                    "\"duration\":135" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_ID.toString());
        }

        @Test
        @DisplayName("Обновление фильма - id : -5")
        public void methodPut_FilmValidFalse_IdNotCorrectTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                "\"id\":-5," +
                                "\"name\":\"test\"," +
                                "\"description\":\"test\"," +
                                "\"releaseDate\":\"2023-01-01\"," +
                                "\"duration\":135" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_ID_MIN.toString());
        }

        @Test
        @DisplayName("Обновление фильма - id : 99")
        public void methodPut_FilmValidFalse_IdNotInCollectionsTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                "\"id\":99," +
                                "\"name\":\"test\"," +
                                "\"description\":\"test\"," +
                                "\"releaseDate\":\"2023-01-01\"," +
                                "\"duration\":135" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(500));

            log.warn(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Обновление фильма - name : null")
        public void methodPut_FilmValidFalse_NameNullTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"description\":\"update\"," +
                                        "\"releaseDate\":\"2010-01-01\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Обновление фильма - name : empty")
        public void methodPut_FilmValidFalse_NameEmptyTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"\"," +
                                        "\"description\":\"update\"," +
                                        "\"releaseDate\":\"2010-01-01\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Обновление фильма - description : null")
        public void methodPut_FilmValidFalse_DescriptionNullTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"update\"," +
                                        "\"releaseDate\":\"2010-01-01\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DESCRIPTION.toString());
        }

        @Test
        @DisplayName("Обновление фильма - description : empty")
        public void methodPut_FilmValidFalse_DescriptionEmptyTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"update\"," +
                                        "\"description\":\"\"," +
                                        "\"releaseDate\":\"2010-01-01\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DESCRIPTION.toString());
        }

        @Test
        @DisplayName("Обновление фильма - description : max length")
        public void methodPut_FilmValidFalse_WhitespaceTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"update\"," +
                                        "\"description\":\"«Того» - это экранизация реальных событий, произошедших зимой 1925 года, \" +\n" +
                                        "                    \"на коварных просторах Аляски, когда поездка из волнующего приключения \" +\n" +
                                        "                    \"переросла в настоящее испытание силы, отваги и решимости. \" +\n" +
                                        "                    \"Когда смертельная эпидемия дифтерии поразила поселение Ном, \" +\n" +
                                        "                    \"а единственное лекарство можно было достать в городе Анкоридже, \" +\n" +
                                        "                    \"расположенном в 1000 км, жители обратились \" +\n" +
                                        "                    \"к лучшему каюру собачьих упряжек в городе - Леонардо Сеппала (Уилльям Дефо).\"," +
                                        "\"releaseDate\":\"2010-01-01\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH.toString());
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : null")
        public void methodPut_FilmValidFalse_ReleaseDataNullTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"update\"," +
                                        "\"description\":\"update\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_RELEASED.toString());
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : min")
        public void methodPut_FilmValidFalse_ReleaseDataMinTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"update\"," +
                                        "\"description\":\"update\"," +
                                        "\"releaseDate\":\"1600-01-01\"," +
                                        "\"duration\":90" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_RELEASED_MIN.toString());
        }

        @Test
        @DisplayName("Обновление фильма - duration : null")
        public void methodPut_FilmValidFalse_DurationNullTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                        .content(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"update\"," +
                                        "\"description\":\"update\"," +
                                        "\"releaseDate\":\"2023-01-01\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DURATION.toString());
        }

        @Test
        @DisplayName("Обновление фильма - duration : min")
        public void methodPut_FilmValidFalse_DurationMinTest() throws Exception {

            mockMvc.perform(
                    put("/films")
                            .content(
                                    "{" +
                                            "\"id\":1," +
                                            "\"name\":\"update\"," +
                                            "\"description\":\"update\"," +
                                            "\"releaseDate\":\"2023-01-01\"," +
                                            "\"duration\":-90" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DURATION_MIN.toString());
        }
    }
}
