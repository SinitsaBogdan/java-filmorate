package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private TotalGenreFilmDao totalGenreFilmDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReviewDao reviewDao;

    @BeforeEach
    public void beforeEach() {

        totalGenreFilmDao.delete();
        filmDao.delete();
        userDao.delete();

        filmDao.insert(101L, 1, "Фильм 1", "", LocalDate.parse("2000-01-01"), 90);
        filmDao.insert(102L, 2, "Фильм 2", "", LocalDate.parse("2000-01-01"), 90);

        userDao.insert(101L, "User-1", LocalDate.parse("2000-01-01"), "user-1", "user1@mail.ru");
        userDao.insert(102L, "User-2", LocalDate.parse("2000-01-01"), "user-2", "user2@mail.ru");

        reviewDao.insert(101L, "content-1", true, 101L, 101L);
        reviewDao.insert(102L, "content-2", false, 102L, 101L);
        reviewDao.insert(103L, "content-3", true, 102L, 102L);

//        totalLikeReviewDao.insert(1L, 1L, true);
//        totalLikeReviewDao.insert(2L, 1L, false);
//        totalLikeReviewDao.insert(2L, 2L, false);
//        totalLikeReviewDao.insert(3L, 1L, true);
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Получение отзыва: ID 1")
        void methodGet_ReviewId1Test() throws Exception {
            mockMvc.perform(get("/reviews/101"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Фильм 1"))
            ;
        }

        @Test
        @DisplayName("Запрос отзыва: ID 9999")
        public void methodGet_ReviewId9999Test() throws Exception {
        }

        @Test
        @DisplayName("Запрос отзыва: ID -1")
        public void methodGet_ReviewIdMinus1Test() throws Exception {
        }

        @Test
        @DisplayName("Получение всех отзывов")
        void methodGet_AllReviews() {
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата отзыва")
        void methodPost_NewReviewValidTrue_AndDoubleFalseTest() throws Exception {
        }

        @Test
        @DisplayName("Добавление нового отзыва - userId : null")
        public void methodPost_NewReviewValidTrue_UserIdNullTest() throws Exception {
        }

        @Test
        @DisplayName("Добавление нового отзыва - filmId : null")
        public void methodPost_NewReviewValidTrue_FilmIdNullTest() throws Exception {
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : null")
        public void methodPost_NewReviewValidTrue_ContentNullTest() throws Exception {
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : empty")
        public void methodPost_NewReviewValidTrue_ContentEmptyTest() throws Exception {
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : max length")
        public void methodPost_NewReviewValidTrue_ContentMaxLengthTest() throws Exception {
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление отзыва")
        void methodPut_ReviewValidTrueTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - id : null")
        public void methodPut_ReviewValidFalse_IdNullTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - id : 99")
        public void methodPut_ReviewValidFalse_IdNotInCollectionsTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - content : empty")
        public void methodPut_ReviewValidFalse_ContentEmptyTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - content : null")
        public void methodPut_ReviewValidFalse_ContentNullTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - useful : -1")
        public void methodPut_ReviewValidFalse_UsefulNegativeTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - добавление лайка")
        public void methodPut_ReviewAddLikeTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление отзыва - добавление дизлайка")
        public void methodPut_ReviewAddDislikeTest() throws Exception {
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление отзыва по ID")
        void methodDelete_DeleteReviewByIdTest() {

        }

        @Test
        @DisplayName("Удаление лайка отзыва")
        void methodDelete_ReviewDeleteLike() {
        }

        @Test
        @DisplayName("Удаление дизлайка отзыва")
        void methodDelete_ReviewDeleteDislike() {
        }
    }
}