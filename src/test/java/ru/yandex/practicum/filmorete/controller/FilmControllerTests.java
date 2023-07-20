package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static String film1;
    private static String film2;
    private static String film3;

    @BeforeAll
    public static void beforeAll() throws JSONException {
        film1 = new JSONObject()
                .put("name", "film1")
                .put("description", "description")
                .put("releaseDate", "2023-01-01")
                .put("duration", 135)
                .toString();

        film2 = new JSONObject()
                .put("name", "film2")
                .put("description", "description")
                .put("releaseDate", "2023-01-01")
                .put("duration", 135)
                .toString();

        film3 = new JSONObject()
                .put("name", "film3")
                .put("description", "description")
                .put("releaseDate", "2023-01-01")
                .put("duration", 135)
                .toString();
    }

    @AfterEach
    public void afterEach() throws Exception {
        mockMvc.perform(delete("/films"))
                .andExpect(status().is(200));
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос всех фильмов")
        public void methodGet_AllFilmListTest() throws Exception {
            mockMvc.perform(get("/films"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("[]"))
                    .andExpect(status().is(200));
        }

        @Test
        @DisplayName("Запрос фильма: ID -1")
        public void methodGet_FilmIdMinus1Test() throws Exception {

            mockMvc.perform(
                    post("/films")
                        .content(film1)
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(get("/films/-1"))
                    .andExpect(status().is(404));
        }

        @Test
        @DisplayName("Запрос фильма: ID 1")
        public void methodGet_FilmId1Test() throws Exception {

            mockMvc.perform(
                            post("/films")
                                    .content(film1)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(get("/films/1"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("{\"id\":1,\"name\":\"film1\",\"description\":\"description\",\"releaseDate\":\"2023-01-01\",\"duration\":135,\"likeUsers\":[],\"sizeLikes\":0}"));
        }

        @Test
        @DisplayName("Запрос фильма: ID 999")
        public void methodGet_FilmId999Test() throws Exception {

            mockMvc.perform(
                            post("/films")
                                    .content(film1)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(get("/films/999"))
                    .andExpect(status().is(404));
        }

        @Test
        @DisplayName("Запрос списка популярных фильмов: ")
        public void methodGet_PopularFilmsToEmptyTest() throws Exception {

            mockMvc.perform(
                            post("/films")
                                    .content(film1)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(
                            post("/films")
                                    .content(film2)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(
                            post("/films")
                                    .content(film3)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(get("/films/popular"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("[{\"id\":1,\"name\":\"film1\",\"description\":\"description\",\"releaseDate\":\"2023-01-01\",\"duration\":135,\"likeUsers\":[],\"sizeLikes\":0},{\"id\":2,\"name\":\"film2\",\"description\":\"description\",\"releaseDate\":\"2023-01-01\",\"duration\":135,\"likeUsers\":[],\"sizeLikes\":0},{\"id\":3,\"name\":\"film3\",\"description\":\"description\",\"releaseDate\":\"2023-01-01\",\"duration\":135,\"likeUsers\":[],\"sizeLikes\":0}]"));

            mockMvc.perform(get("/films/popular?count=1"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("[{\"id\":1,\"name\":\"film1\",\"description\":\"description\",\"releaseDate\":\"2023-01-01\",\"duration\":135,\"likeUsers\":[],\"sizeLikes\":0}]"));
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
                                    .content(film1)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(
                            post("/films")
                                    .content(film1)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - name : null")
        public void methodPost_NewFilmValidFalse_NameNullTest() throws Exception {

            String film = new JSONObject()
                    .put("description", "description")
                    .put("releaseDate", "2023-01-01")
                    .put("duration", 135)
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - name : empty")
        public void methodPost_NewFilmValidFalse_NameEmptyTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "")
                    .put("description", "description")
                    .put("releaseDate", "2023-01-01")
                    .put("duration", 135)
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : null")
        public void methodPost_NewFilmValidFalse_DescriptionNullTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "film2")
                    .put("releaseDate", "2023-01-01")
                    .put("duration", 135)
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DESCRIPTION.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - descriptions : max length")
        public void methodPost_NewFilmValidFalse_DescriptionMaxLengthTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "film2")
                    .put("description", "«Того» - это экранизация реальных событий, произошедших зимой 1925 года, " +
                            "на коварных просторах Аляски, когда поездка из волнующего приключения переросла " +
                            "в настоящее испытание силы, отваги и решимости. " +
                            "Когда смертельная эпидемия дифтерии поразила поселение Ном, " +
                            "а единственное лекарство можно было достать в городе Анкоридже, расположенном в 1000 км, " +
                            "жители обратились к лучшему каюру собачьих упряжек в городе " +
                            "- Леонардо Сеппала (Уилльям Дефо).")
                    .put("releaseDate", "2023-01-01")
                    .put("duration", 135)
                    .toString();

            mockMvc.perform(
                    post("/films")
                            .content(film)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : null")
        public void methodPost_NewFilmValidFalse_ReleaseDateNullTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "film2")
                    .put("description", "description")
                    .put("duration", 135)
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_RELEASED.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - releaseDate : min date")
        public void methodPost_NewFilmValidFalse_ReleaseDateMinDataTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "film2")
                    .put("description", "description")
                    .put("releaseDate", "1500-01-01")
                    .put("duration", 135)
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_RELEASED_MIN.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : null")
        public void methodPost_NewFilmValidFalse_DurationNullTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "film2")
                    .put("description", "description")
                    .put("releaseDate", "2023-01-01")
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DURATION.toString());
        }

        @Test
        @DisplayName("Добавление нового фильма - durations : min")
        public void methodPost_NewFilmValidFalse_DurationMinTest() throws Exception {

            String film = new JSONObject()
                    .put("name", "film2")
                    .put("description", "description")
                    .put("releaseDate", "2023-01-01")
                    .put("duration", -1)
                    .toString();

            mockMvc.perform(
                            post("/films")
                                    .content(film)
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
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
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
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("name", "update")
                    .put("description", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_ID.toString());
        }

        @Test
        @DisplayName("Обновление фильма - id : -5")
        public void methodPut_FilmValidFalse_IdNotCorrectTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 5)
                    .put("name", "update")
                    .put("description", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            log.warn(VALID_ERROR_FILM_ID_MIN.toString());
        }

        @Test
        @DisplayName("Обновление фильма - id : 99")
        public void methodPut_FilmValidFalse_IdNotInCollectionsTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 99)
                    .put("name", "update")
                    .put("description", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            log.warn(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Обновление фильма - name : null")
        public void methodPut_FilmValidFalse_NameNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("description", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Обновление фильма - name : empty")
        public void methodPut_FilmValidFalse_NameEmptyTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "")
                    .put("description", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_NAME.toString());
        }

        @Test
        @DisplayName("Обновление фильма - description : null")
        public void methodPut_FilmValidFalse_DescriptionNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DESCRIPTION.toString());
        }

        @Test
        @DisplayName("Обновление фильма - description : empty")
        public void methodPut_FilmValidFalse_DescriptionEmptyTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DESCRIPTION.toString());
        }

        @Test
        @DisplayName("Обновление фильма - description : max length")
        public void methodPut_FilmValidFalse_WhitespaceTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "«Того» - это экранизация реальных событий, произошедших зимой 1925 года," +
                            "на коварных просторах Аляски, когда поездка из волнующего приключения переросла" +
                            "в настоящее испытание силы, отваги и решимости." +
                            "Когда смертельная эпидемия дифтерии поразила поселение Ном," +
                            "а единственное лекарство можно было достать в городе Анкоридже, расположенном в 1000 км," +
                            "жители обратились к лучшему каюру собачьих упряжек в городе" +
                            "- Леонардо Сеппала (Уилльям Дефо).")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH.toString());
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : null")
        public void methodPut_FilmValidFalse_ReleaseDataNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "update")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_RELEASED.toString());
        }

        @Test
        @DisplayName("Обновление фильма - releaseData : min")
        public void methodPut_FilmValidFalse_ReleaseDataMinTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "")
                    .put("releaseDate", "1500-01-01")
                    .put("duration", 90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_RELEASED_MIN.toString());
        }

        @Test
        @DisplayName("Обновление фильма - duration : null")
        public void methodPut_FilmValidFalse_DurationNullTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "")
                    .put("releaseDate", "2010-01-01")
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_NOT_DURATION.toString());
        }

        @Test
        @DisplayName("Обновление фильма - duration : min")
        public void methodPut_FilmValidFalse_DurationMinTest() throws Exception {

            mockMvc.perform(
                    post("/films")
                            .content(film1)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            String film = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("description", "")
                    .put("releaseDate", "2010-01-01")
                    .put("duration", -90)
                    .toString();

            mockMvc.perform(
                            put("/films")
                                    .content(film)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_FILM_DURATION_MIN.toString());
        }
    }
}
