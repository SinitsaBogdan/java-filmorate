package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void beforeEach() throws Exception {

        user = User.builder()
                .name("Bogdan")
                .birthday(LocalDate.of(1997, 4, 11))
                .login("SinitsaBogdan")
                .email("mail@mail.ru")
                .build();

        mockMvc.perform(
                post("/users")
                    .content(objectMapper.writeValueAsString(user))
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

            user = User.builder()
                    .name("Bogdan")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .login("Sinitsa")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : null")
        public void methodPost_NewUserValidTrue_NameNullTest() throws Exception {

            user = User.builder()
                    .login("Sinitsa")
                    .email("mail2@mail.ru")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.name").value("Sinitsa"));
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : empty")
        public void methodPost_NewUserValidTrue_NameEmptyTest() throws Exception {

            user = User.builder()
                    .name("")
                    .login("Sinitsa")
                    .email("mail2@mail.ru")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.name").value("Sinitsa"));
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : null")
        public void methodPost_NewUserValidFalse_BirthdayNullTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("Sinitsa")
                    .email("mail2@mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_BIRTHDAY),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : null")
        public void methodPost_NewUserValidFalse_LoginNullTest() throws Exception {

            user = User.builder()
                    .name("")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .email("mail2@mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_LOGIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : empty")
        public void methodPost_NewUserValidFalse_LoginEmptyTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .email("mail2@mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_LOGIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : not whitespace")
        public void methodPost_NewUserValidFalse_LoginNotWhitespaceTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("Sinitsa Bogdan")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .email("mail2@mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_LOGIN_IS_WHITESPACE),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : null")
        public void methodPost_NewUserValidFalse_EmailNullTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("Sinitsa")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_EMAIL),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : empty")
        public void methodPost_NewUserValidFalse_EmailEmptyTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("Sinitsa")
                    .email("")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_EMAIL),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : double")
        public void methodPost_NewUserValidFalse_EmailDoubleTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("Sinitsa")
                    .birthday(LocalDate.of(1997, 4, 11))
                    .email("mail2.mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_EMAIL_NOT_CORRECT),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : after actual")
        public void methodPost_NewUserValidFalse_BirthdayAfterActualTest() throws Exception {

            user = User.builder()
                    .name("Bogdan")
                    .login("Sinitsa")
                    .birthday(LocalDate.of(3023, 4, 11))
                    .email("mail2@mail.ru")
                    .build();

            mockMvc.perform(
                    post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_BIRTHDAY_MAX),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление пользователя")
        public void methodPut_UserValidTrueTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                            .content(objectMapper.writeValueAsString(user))
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

            user = User.builder()
                    .name("update")
                    .login("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_ID),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - id : -5")
        public void methodPut_UserValidFalse_IdNotCorrectTest() throws Exception {

            user = User.builder()
                    .id(-5)
                    .name("update")
                    .login("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_ID_NOT_CORRECT),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - id : 99")
        public void methodPut_UserValidFalse_IdNotInCollectionsTest() throws Exception {

            user = User.builder()
                    .id(99)
                    .name("update")
                    .login("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(500))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("500 INTERNAL_SERVER_ERROR \"%s\"", VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : null")
        public void methodPut_UserValidFalse_BirthdayNullTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_BIRTHDAY),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : 3997")
        public void methodPut_UserValidFalse_BirthdayAfterActualTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(3995, 4, 11))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_BIRTHDAY_MAX),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - login : null")
        public void methodPut_UserValidFalse_LoginNullTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_LOGIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - login : empty")
        public void methodPut_UserValidFalse_LoginEmptyTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .login("")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_LOGIN),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - login : whitespace")
        public void methodPut_UserValidFalse_WhitespaceTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .login("Sinitsa Bogdan")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_LOGIN_IS_WHITESPACE),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - email : null")
        public void methodPut_UserValidFalse_EmailNullTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .login("update")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_EMAIL),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - email : empty")
        public void methodPut_UserValidFalse_EmailEmptyTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .login("update")
                    .email("")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_NOT_EMAIL),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }

        @Test
        @DisplayName("Обновление пользователя - email : not corrected")
        public void methodPut_UserValidFalse_EmailNotCorrectedTest() throws Exception {

            user = User.builder()
                    .id(1)
                    .name("update")
                    .birthday(LocalDate.of(1995, 4, 11))
                    .login("update")
                    .email("mail.mail.ru")
                    .build();

            mockMvc.perform(
                    put("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                    .andExpect(result -> assertEquals(
                            String.format("400 BAD_REQUEST \"%s\"", VALID_ERROR_USER_EMAIL_NOT_CORRECT),
                            Objects.requireNonNull(result.getResolvedException()).getMessage())
                    );
        }
    }
}