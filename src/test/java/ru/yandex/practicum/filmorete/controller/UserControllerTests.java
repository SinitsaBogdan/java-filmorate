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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static String user1;
    private static String user2;

    @BeforeAll
    public static void beforeAll() throws JSONException {
        user1 = new JSONObject()
                .put("name", "User1")
                .put("birthday", "1997-04-11")
                .put("login", "User1")
                .put("email", "User1@mail.ru")
                .toString();

        user2 = new JSONObject()
                .put("name", "User2")
                .put("birthday", "1997-04-11")
                .put("login", "User2")
                .put("email", "User2@mail.ru")
                .toString();
    }

    @AfterEach
    public void afterEach() throws Exception {
        mockMvc.perform(delete("/users"))
                .andExpect(status().is(200));
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос всех пользователей")
        public void methodGet_AllUserListTest() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("[]"));
        }

        @Test
        @DisplayName("Запрос пользователя: ID -1")
        public void methodGet_UserIdMinus1Test() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(get("/users/-1"))
                    .andExpect(status().is(404));
        }

        @Test
        @DisplayName("Запрос пользователя: ID 1")
        public void methodGet_UserId1Test() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(get("/users/1"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("{\"id\":1,\"name\":\"User1\",\"birthday\":\"1997-04-11\",\"login\":\"User1\",\"email\":\"User1@mail.ru\",\"likesFilms\":[],\"friends\":[],\"sizeFriends\":0,\"sizeLikes\":0}"));
        }

        @Test
        @DisplayName("Запрос пользователя: ID 9999")
        public void methodGet_UserId9999Test() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(get("/users/9999"))
                    .andExpect(status().is(404));
        }

        @Test
        @DisplayName("Запрос списка друзей пользователя: ID 1 - []")
        public void methodGet_FriendsUserId1ToEmptyTest() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(get("/users/1/friends"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("[]"));
        }

        @Test
        @DisplayName("Запрос списка общих друзей пользователей: ID 1 и ID 2 - []")
        public void methodGet_CommonFriendsUserId1AndId2ToEmptyTest() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(post("/users")
                    .content(user2)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(get("/users/1/friends/common/2"))
                    .andExpect(status().is(200))
                    .andExpect(content().string("[]"));
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата")
        public void methodPost_NewUserValidTrue_AndDoubleFalseTest() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : null")
        public void methodPost_NewUserValidTrue_NameNullTest() throws Exception {

            String user = new JSONObject()
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.name").value("SinitsaBogdan"));
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : empty")
        public void methodPost_NewUserValidTrue_NameEmptyTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.name").value("SinitsaBogdan"));
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : null")
        public void methodPost_NewUserValidFalse_BirthdayNullTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_BIRTHDAY.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : null")
        public void methodPost_NewUserValidFalse_LoginNullTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : empty")
        public void methodPost_NewUserValidFalse_LoginEmptyTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - login : not whitespace")
        public void methodPost_NewUserValidFalse_LoginNotWhitespaceTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "Sinitsa Bogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_LOGIN_IS_WHITESPACE.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : null")
        public void methodPost_NewUserValidFalse_EmailNullTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - email : empty")
        public void methodPost_NewUserValidFalse_EmailEmptyTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Добавление нового пользователя - birthday : after actual")
        public void methodPost_NewUserValidFalse_BirthdayAfterActualTest() throws Exception {

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "3997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(post("/users")
                            .content(user)
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

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "update")
                    .put("birthday", "1995-04-11")
                    .put("login", "update")
                    .put("email", "update@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
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

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_ID.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - id : -5")
        public void methodPut_UserValidFalse_IdNotCorrectTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", -5)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_ID_NOT_CORRECT.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - id : 99")
        public void methodPut_UserValidFalse_IdNotInCollectionsTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 99)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            log.warn(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : null")
        public void methodPut_UserValidFalse_BirthdayNullTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_BIRTHDAY.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - birthday : 3997")
        public void methodPut_UserValidFalse_BirthdayAfterActualTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "3997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_BIRTHDAY_MAX.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - login : null")
        public void methodPut_UserValidFalse_LoginNullTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - login : empty")
        public void methodPut_UserValidFalse_LoginEmptyTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_LOGIN.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - login : whitespace")
        public void methodPut_UserValidFalse_WhitespaceTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "Sinitsa Bogdan")
                    .put("email", "User3@mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_LOGIN_IS_WHITESPACE.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - email : null")
        public void methodPut_UserValidFalse_EmailNullTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - email : empty")
        public void methodPut_UserValidFalse_EmailEmptyTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_NOT_EMAIL.toString());
        }

        @Test
        @DisplayName("Обновление пользователя - email : not corrected")
        public void methodPut_UserValidFalse_EmailNotCorrectedTest() throws Exception {

            mockMvc.perform(post("/users")
                            .content(user1)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            String user = new JSONObject()
                    .put("id", 1)
                    .put("name", "Sinitsa Bogdan")
                    .put("birthday", "1997-04-11")
                    .put("login", "SinitsaBogdan")
                    .put("email", "User3.mail.ru")
                    .toString();

            mockMvc.perform(put("/users")
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));

            log.warn(VALID_ERROR_USER_EMAIL_NOT_CORRECT.toString());
        }

        @Test
        @DisplayName("Добавление пользователя в список друзей")
        public void methodPut_AddFriendUserTest() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(post("/users")
                    .content(user2)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(put("/users/1/friends/2")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(put("/users/1/friends/9999")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            mockMvc.perform(put("/users/1/friends/-1")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            mockMvc.perform(get("/users/1/friends")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(content().string("[{\"id\":2,\"name\":\"User2\",\"birthday\":\"1997-04-11\",\"login\":\"User2\",\"email\":\"User2@mail.ru\",\"likesFilms\":[],\"friends\":[1],\"sizeFriends\":1,\"sizeLikes\":0}]"));
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление пользователя из списка друзей")
        public void methodPut_AddFriendUserTest() throws Exception {

            mockMvc.perform(post("/users")
                    .content(user1)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(post("/users")
                    .content(user2)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            mockMvc.perform(delete("/users/1/friends/2")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200));

            mockMvc.perform(delete("/users/1/friends/9999")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            mockMvc.perform(delete("/users/1/friends/-1")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(404));

            mockMvc.perform(get("/users/1/friends")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(content().string("[]"));
        }
    }
}