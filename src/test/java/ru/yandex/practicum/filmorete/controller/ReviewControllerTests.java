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
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalGenreFilmDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTests {

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

    /*@Autowired
    private ReviewDao reviewDao;
    */



    private final Review duplicate = Review.builder()
            .id(1L)
            .content("content-1")
            .isPositive(false)
            .userId(1L)
            .filmId(1L)
            .typeId(1L)
            .evaluationId(1L)
            .useful(1)
            .build();

    @BeforeEach
    public void beforeEach() {

        totalGenreFilmDao.delete();
        filmDao.delete();
        userDao.delete();
        //reviewDao.delete();

        filmDao.insert(1L, 1, "Фильм 1", "", LocalDate.parse("2000-01-01"), 90);
        filmDao.insert(2L, 2, "Фильм 2", "", LocalDate.parse("2000-01-01"), 90);

        totalGenreFilmDao.insert(1L, 1);
        totalGenreFilmDao.insert(2L, 2);

        userDao.insert(1L, "User-1", LocalDate.parse("2000-01-01"), "user-1", "user1@mail.ru");
        userDao.insert(2L, "User-2", LocalDate.parse("2000-01-01"), "user-2", "user2@mail.ru");

        //reviewDao.insert(1L,"content-1",false,1L,1L,1L,1L,1);
        //reviewDao.insert(2L,"content-2",true,2L,1L,1L,1L,2);
        //reviewDao.insert(3L,"content-3",true,2L,2L,1L,1L,3);

        //totalReviewLikeDao.insert(1L, 1L); //(reviewId, userId)
        //totalReviewLikeDao.insert(2L, 1L);
        //totalReviewLikeDao.insert(2L, 2L);
        //totalReviewLikeDao.insert(3L, 1L);
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {
        @Test
        @DisplayName("Получение отзыва: ID 1")
        void methodGet_ReviewId1Test() {
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
            Review duplicate = Review.builder()
                    .id(1L)
                    .content("content-1")
                    .isPositive(false)
                    .userId(null)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Добавление нового отзыва - filmId : null")
        public void methodPost_NewReviewValidTrue_FilmIdNullTest() throws Exception {
            Review duplicate = Review.builder()
                    .id(1L)
                    .content("content-1")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(null)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : null")
        public void methodPost_NewReviewValidTrue_ContentNullTest() throws Exception {
            Review duplicate = Review.builder()
                    .id(1L)
                    .content(null)
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : empty")
        public void methodPost_NewReviewValidTrue_ContentEmptyTest() throws Exception {
            Review duplicate = Review.builder()
                    .id(1L)
                    .content("")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : max length")
        public void methodPost_NewReviewValidTrue_ContentMaxLengthTest() throws Exception {
            Review duplicate = Review.builder()
                    .id(1L)
                    .content("Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание. Очень длинное описание.")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
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
            Review reviews = Review.builder()
                    .id(null)
                    .content("content-1")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Обновление отзыва - id : 99")
        public void methodPut_ReviewValidFalse_IdNotInCollectionsTest() throws Exception {
            Review reviews = Review.builder()
                    .id(99L)
                    .content("content-1")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Обновление отзыва - content : empty")
        public void methodPut_ReviewValidFalse_ContentEmptyTest() throws Exception {
            Review reviews = Review.builder()
                    .id(1L)
                    .content("")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Обновление отзыва - content : null")
        public void methodPut_ReviewValidFalse_ContentNullTest() throws Exception {
            Review reviews = Review.builder()
                    .id(1L)
                    .content(null)
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(1)
                    .build();
        }

        @Test
        @DisplayName("Обновление отзыва - useful : -1")
        public void methodPut_ReviewValidFalse_UsefulNegativeTest() throws Exception {
            Review reviews = Review.builder()
                    .id(1L)
                    .content("content-1")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(-1)
                    .build();
        }

        @Test
        @DisplayName("Обновление отзыва - добавление лайка")
        public void methodPut_ReviewAddLikeTest() throws Exception {
            Review reviews = Review.builder()
                    .id(1L)
                    .content("content-1")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(-1)
                    .build();
        }

        @Test
        @DisplayName("Обновление отзыва - добавление дизлайка")
        public void methodPut_ReviewAddDislikeTest() throws Exception {
            Review reviews = Review.builder()
                    .id(1L)
                    .content("content-1")
                    .isPositive(false)
                    .userId(1L)
                    .filmId(1L)
                    .typeId(1L)
                    .evaluationId(1L)
                    .useful(-1)
                    .build();
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