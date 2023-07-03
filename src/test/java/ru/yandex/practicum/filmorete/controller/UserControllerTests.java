package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() throws Exception {

        mockMvc.perform(
                post("/users")
                    .content(
                            "{" +
                            "\"name\":\"Bogdan\"," +
                            "\"birthday\":\"1997-04-11\"," +
                            "\"login\":\"SinitsaBogdan\"," +
                            "\"email\":\"mail@mail.ru\"" +
                            "}"
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Bogdan"))
                .andExpect(jsonPath("$.birthday").value("1997-04-11"))
                .andExpect(jsonPath("$.login").value("SinitsaBogdan"))
                .andExpect(jsonPath("$.email").value("mail@mail.ru"));
    }

    @AfterEach
    public void afterEach() throws Exception {
        mockMvc.perform(delete("/users"))
                .andExpect(status().isOk());
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос всех пользователей")
        public void methodGet_AllUserList_EmptyTest() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("[{\"id\":1,\"name\":\"Bogdan\",\"birthday\":\"1997-04-11\",\"login\":\"SinitsaBogdan\",\"email\":\"mail@mail.ru\"}]"));
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата")
        public void methodPost_NewUserValidTrue_AndDoubleFalseTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"SinitsaBogdan\"," +
                                    "\"email\":\"mail@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : null")
        public void methodPost_NewUserValidTrue_NameNullTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"SinitsaBogdan\"," +
                                    "\"email\":\"mail2@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.name").value("SinitsaBogdan"));
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : empty")
        public void methodPost_NewUserValidTrue_NameEmptyTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"SinitsaBogdan\"," +
                                    "\"email\":\"mail2@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.name").value("SinitsaBogdan"));
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : null")
        public void methodPost_NewUserValidFalse_BirthdayNullTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"login\":\"SinitsaBogdan\"," +
                                    "\"email\":\"mail2@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_BIRTHDAY.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : null")
        public void methodPost_NewUserValidFalse_LoginNullTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"email\":\"mail2@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : empty")
        public void methodPost_NewUserValidFalse_LoginEmptyTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"\"," +
                                    "\"email\":\"mail2@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : not whitespace")
        public void methodPost_NewUserValidFalse_LoginNotWhitespaceTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"Sinitsa Bogdan\"," +
                                    "\"email\":\"mail2@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_LOGIN_IS_WHITESPACE.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : null")
        public void methodPost_NewUserValidFalse_EmailNullTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"SinitsaBogdan\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : empty")
        public void methodPost_NewUserValidFalse_EmailEmptyTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"SinitsaBogdan\"," +
                                    "\"email\":\"\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : after actual")
        public void methodPost_NewUserValidFalse_BirthdayAfterActualTest() throws Exception {

            mockMvc.perform(
                    post("/users")
                            .content(
                                    "{" +
                                    "\"name\":\"Bogdan\"," +
                                    "\"birthday\":\"3997-04-11\"," +
                                    "\"login\":\"SinitsaBogdan\"," +
                                    "\"email\":\"mail@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_BIRTHDAY_MAX.toString());
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление пользователя")
        public void methodPut_UserValidTrueTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                            .content(
                                    "{" +
                                    "\"id\":1," +
                                    "\"name\":\"update\"," +
                                    "\"birthday\":\"1995-04-11\"," +
                                    "\"login\":\"update\"," +
                                    "\"email\":\"update@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("update"))
                    .andExpect(jsonPath("$.birthday").value("1995-04-11"))
                    .andExpect(jsonPath("$.login").value("update"))
                    .andExpect(jsonPath("$.email").value("update@mail.ru"));
        }

        @Test
        @DisplayName("Обновление пользователя - id : null")
        public void methodPut_UserValidFalse_IdNullTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"update\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_ID.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - id : -5")
        public void methodPut_UserValidFalse_IdNotCorrectTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                            .content(
                                    "{" +
                                    "\"id\":-5," +
                                    "\"name\":\"update\"," +
                                    "\"birthday\":\"1997-04-11\"," +
                                    "\"login\":\"update\"," +
                                    "\"email\":\"update@mail.ru\"" +
                                    "}"
                            )
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_ID_NOT_CORRECT.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - id : 99")
        public void methodPut_UserValidFalse_IdNotInCollectionsTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":99," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"update\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(500));

            log.warn(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : null")
        public void methodPut_UserValidFalse_BirthdayNullTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"login\":\"update\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_BIRTHDAY.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : 3997")
        public void methodPut_UserValidFalse_BirthdayAfterActualTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"3997-04-11\"," +
                                "\"login\":\"update\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_BIRTHDAY_MAX.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - login : null")
        public void methodPut_UserValidFalse_LoginNullTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - login : empty")
        public void methodPut_UserValidFalse_LoginEmptyTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - login : whitespace")
        public void methodPut_UserValidFalse_WhitespaceTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"upd ate\"," +
                                "\"email\":\"update@mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_LOGIN_IS_WHITESPACE.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - email : null")
        public void methodPut_UserValidFalse_EmailNullTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"update\"," +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - email : empty")
        public void methodPut_UserValidFalse_EmailEmptyTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"update\"," +
                                "\"email\":\"\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - email : not corrected")
        public void methodPut_UserValidFalse_EmailNotCorrectedTest() throws Exception {

            mockMvc.perform(
                    put("/users")
                        .content(
                                "{" +
                                "\"id\":1," +
                                "\"name\":\"update\"," +
                                "\"birthday\":\"1997-04-11\"," +
                                "\"login\":\"update\"," +
                                "\"email\":\"update.mail.ru\"" +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_EMAIL_NOT_CORRECT.toString());
        }
    }
}