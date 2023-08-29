package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.impl.UserDaoImpl;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDaoImpl userDao;

    private final User duplicate = User.builder().name("User-1").birthday(LocalDate.parse("2000-01-01")).login("user-1").email("user1@mail.ru").build();

    @BeforeEach
    public void beforeEach() {
        userDao.delete();
        userDao.insert(101L, "User-1", LocalDate.parse("2000-01-01"), "user-1", "user1@mail.ru");
        userDao.insert(102L, "User-2", LocalDate.parse("2000-01-01"), "user-2", "user2@mail.ru");
        userDao.insert(103L, "User-3", LocalDate.parse("2000-01-01"), "user-3", "user3@mail.ru");
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос всех пользователей")
        public void methodGet_AllUserListTest() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3))
            ;
        }

        @Test
        @DisplayName("Запрос пользователя: ID -1")
        public void methodGet_UserIdMinus1Test() throws Exception {
            mockMvc.perform(get("/users/-1"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Запрос пользователя: ID 9999")
        public void methodGet_UserId9999Test() throws Exception {
            mockMvc.perform(get("/users/9999"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Запрос пользователя: ID 101")
        public void methodGet_UserId1Test() throws Exception {
            mockMvc.perform(get("/users/101"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(101))
                    .andExpect(jsonPath("$.name").value("User-1"))
                    .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                    .andExpect(jsonPath("$.login").value("user-1"))
                    .andExpect(jsonPath("$.email").value("user1@mail.ru"))
            ;
        }

        @Test
        @DisplayName("Запрос списка друзей пользователя")
        public void methodGet_FriendsUserId1ToEmptyTest() throws Exception {
            mockMvc.perform(get("/users/101/friends"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0))
            ;
        }

        @Test
        @DisplayName("Запрос списка общих друзей пользователей")
        public void methodGet_CommonFriendsUserId1AndId2ToEmptyTest() throws Exception {
            mockMvc.perform(get("/users/101/friends/common/102"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0))
            ;
        }

        @Test
        @DisplayName("Запрос списка рекомендации по фильмам")
        public void methodGet_FilmRecommendationsByUserIdTest() throws Exception {
        }

        @Test
        @DisplayName("Запрос ленты событий пользователя")
        public void methodGet_FeedByUserIdTest() throws Exception {
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата")
        public void methodPost_NewUserValidTrue_AndDoubleFalseTest() throws Exception {
            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(duplicate))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : null")
        public void methodPost_NewUserValidTrue_NameNullTest() throws Exception {
            User user = User.builder()
                    .id(125L)
                    .name(null)
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("login")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : empty")
        public void methodPost_NewUserValidTrue_NameEmptyTest() throws Exception {
            User user = User.builder()
                    .id(120L)
                    .name("")
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("login1")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : null")
        public void methodPost_NewUserValidFalse_BirthdayNullTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name(null)
                    .birthday(null)
                    .login("login")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : null")
        public void methodPost_NewUserValidFalse_LoginNullTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name(null)
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login(null)
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : empty")
        public void methodPost_NewUserValidFalse_LoginEmptyTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name(null)
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : not whitespace")
        public void methodPost_NewUserValidFalse_LoginNotWhitespaceTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name(null)
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("log in")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : null")
        public void methodPost_NewUserValidFalse_EmailNullTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("user")
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("login")
                    .email(null)
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : empty")
        public void methodPost_NewUserValidFalse_EmailEmptyTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("user")
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("login")
                    .email("")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : after actual")
        public void methodPost_NewUserValidFalse_BirthdayAfterActualTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("user")
                    .birthday(LocalDate.parse("2100-01-01"))
                    .login("login")
                    .email("mail@mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление пользователя - email : not corrected")
        public void methodPut_UserValidFalse_EmailNotCorrectedTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("mail")
                    .birthday(LocalDate.parse("2000-01-01"))
                    .login("mail")
                    .email("mail/mail.ru")
                    .build();

            mockMvc.perform(post("/users")
                            .content(objectMapper.writeValueAsString(user))
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
        @DisplayName("Обновление пользователя")
        public void methodPut_UserValidTrueTest() throws Exception {
            User user = User.builder()
                    .id(101L)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;

            mockMvc.perform(get("/users/101"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(101))
                    .andExpect(jsonPath("$.name").value("update"))
                    .andExpect(jsonPath("$.birthday").value("2010-01-01"))
                    .andExpect(jsonPath("$.login").value("update"))
                    .andExpect(jsonPath("$.email").value("update@mail.ru"))
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - id : null")
        public void methodPut_UserValidFalse_IdNullTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - id : -5")
        public void methodPut_UserValidFalse_IdNotCorrectTest() throws Exception {
            User user = User.builder()
                    .id(-5L)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - id : 99")
        public void methodPut_UserValidFalse_IdNotInCollectionsTest() throws Exception {
            User user = User.builder()
                    .id(99L)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : null")
        public void methodPut_UserValidFalse_BirthdayNullTest() throws Exception {
            User user = User.builder()
                    .id(101L)
                    .name("update")
                    .birthday(null)
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : 3997")
        public void methodPut_UserValidFalse_BirthdayAfterActualTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("3997-01-01"))
                    .login("update")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - login : null")
        public void methodPut_UserValidFalse_LoginNullTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login(null)
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - login : empty")
        public void methodPut_UserValidFalse_LoginEmptyTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - login : whitespace")
        public void methodPut_UserValidFalse_WhitespaceTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("upda te")
                    .email("update@mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - email : null")
        public void methodPut_UserValidFalse_EmailNullTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email(null)
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - email : empty")
        public void methodPut_UserValidFalse_EmailEmptyTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email("")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление пользователя - email : not corrected")
        public void methodPut_UserValidFalse_EmailNotCorrectedTest() throws Exception {
            User user = User.builder()
                    .id(null)
                    .name("update")
                    .birthday(LocalDate.parse("2010-01-01"))
                    .login("update")
                    .email("update/mail.ru")
                    .build();

            mockMvc.perform(put("/users")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление пользователя в список друзей")
        public void methodPut_AddFriendUserTest() throws Exception {
            mockMvc.perform(put("/users/101/friends/102"))
                    .andExpect(status().isOk())
            ;
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление пользователя по ID")
        public void methodDelete_DeleteUserSearchIdTest() throws Exception {
            mockMvc.perform(delete("/users/101"))
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
            ;
        }

        @Test
        @DisplayName("Удаление не существующего пользователя - id : 101")
        public void methodDelete_DeleteFilmByIdTest() throws Exception {
            mockMvc.perform(delete("/users/201")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Удаление пользователя из списка друзей")
        public void methodDelete_DeleteFriendUserTest() throws Exception {
            mockMvc.perform(delete("/users/101/friends/102"))
                    .andExpect(status().isOk())
            ;
        }
    }
}