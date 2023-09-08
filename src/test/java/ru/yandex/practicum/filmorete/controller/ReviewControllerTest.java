package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private TotalLikeReviewDao totalLikeReviewDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReviewDao reviewDao;

    @BeforeEach
    public void beforeEach() {

        filmDao.deleteAll();
        userDao.deleteAll();
        reviewDao.deleteAll();
        totalLikeReviewDao.deleteAll();

        filmDao.insert(101L, 1, "Фильм 1", "", LocalDate.parse("2000-01-01"), 90);

        userDao.insert(101L, "User-1", LocalDate.parse("2000-01-01"), "user-1", "user1@mail.ru");
        userDao.insert(102L, "User-2", LocalDate.parse("2000-01-01"), "user-2", "user2@mail.ru");
        userDao.insert(103L, "User-3", LocalDate.parse("2000-01-01"), "user-3", "user3@mail.ru");

        reviewDao.insert(101L, "content-1", true, 101L, 101L);

        totalLikeReviewDao.insert(101L, 101L, true);
        totalLikeReviewDao.insert(101L, 102L, false);
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Получение отзыва: ID 101")
        void methodGet_ReviewId1Test() throws Exception {
            mockMvc.perform(get("/reviews/101"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reviewId").value(101))
                    .andExpect(jsonPath("$.content").value("content-1"))
                    .andExpect(jsonPath("$.isPositive").value("true"))
                    .andExpect(jsonPath("$.userId").value(101))
                    .andExpect(jsonPath("$.filmId").value(101))
                    .andExpect(jsonPath("$.useful").value(0))
            ;
        }

        @Test
        @DisplayName("Запрос отзыва: ID 9999")
        public void methodGet_ReviewId9999Test() throws Exception {
            mockMvc.perform(get("/reviews/9999"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Запрос отзыва: ID -1")
        public void methodGet_ReviewIdMinus1Test() throws Exception {
            mockMvc.perform(get("/reviews/-1"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Получение всех отзывов")
        void methodGet_AllReviews() throws Exception {
            mockMvc.perform(get("/reviews"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
            ;
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Добавление нового отзыва - userId : null")
        public void methodPost_NewReviewValidTrue_UserIdNullTest() throws Exception {
            Review review = Review.builder().reviewId(200L).content("content-200").isPositive(false).userId(3L).filmId(1L).build();
            mockMvc.perform(post("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового отзыва - filmId : null")
        public void methodPost_NewReviewValidTrue_FilmIdNullTest() throws Exception {
            Review review = Review.builder().reviewId(200L).content("content-200").isPositive(false).userId(3L).filmId(null).build();
            mockMvc.perform(post("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : null")
        public void methodPost_NewReviewValidTrue_ContentNullTest() throws Exception {
            Review review = Review.builder().reviewId(200L).content(null).isPositive(false).userId(3L).filmId(2L).build();
            mockMvc.perform(post("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового отзыва - content : empty")
        public void methodPost_NewReviewValidTrue_ContentEmptyTest() throws Exception {
            Review review = Review.builder().reviewId(200L).content("").isPositive(false).userId(3L).filmId(2L).build();
            mockMvc.perform(post("/reviews")
                            .content(objectMapper.writeValueAsString(review))
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
        @DisplayName("Обновление отзыва")
        void methodPut_ReviewValidTrueTest() throws Exception {
            Review review = Review.builder().reviewId(101L).content("Обновление").isPositive(true).userId(3L).filmId(2L).build();
            mockMvc.perform(put("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Обновление отзыва - id : null")
        public void methodPut_ReviewValidFalse_IdNullTest() throws Exception {
            Review review = Review.builder().reviewId(null).content("Обновление").isPositive(true).userId(3L).filmId(2L).build();
            mockMvc.perform(put("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление отзыва - id : 9999L")
        public void methodPut_ReviewValidFalse_IdNotInCollectionsTest() throws Exception {
            Review review = Review.builder().reviewId(9999L).content("Обновление").isPositive(true).userId(3L).filmId(2L).build();
            mockMvc.perform(put("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление отзыва - content : empty")
        public void methodPut_ReviewValidFalse_ContentEmptyTest() throws Exception {
            Review review = Review.builder().reviewId(102L).content("").isPositive(true).userId(3L).filmId(2L).build();
            mockMvc.perform(put("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление отзыва - content : null")
        public void methodPut_ReviewValidFalse_ContentNullTest() throws Exception {
            Review review = Review.builder().reviewId(102L).content(null).isPositive(true).userId(3L).filmId(2L).build();
            mockMvc.perform(put("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление отзыва - useful : -1")
        public void methodPut_ReviewValidFalse_UsefulNegativeTest() throws Exception {
            Review review = Review.builder().reviewId(101L).content("Обновление").isPositive(true).userId(3L).filmId(2L).useful(-1).build();
            mockMvc.perform(put("/reviews")
                            .content(objectMapper.writeValueAsString(review))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Добавление лайка к отзыву")
        public void methodPut_ReviewAddLikeTest() throws Exception {
            mockMvc.perform(put("/reviews/101/like/103")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Добавление дизлайка к отзыву")
        public void methodPut_ReviewAddDislikeTest() throws Exception {
            mockMvc.perform(put("/reviews/101/dislike/103")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление отзыва по ID")
        void methodDelete_DeleteReviewByIdTest() throws Exception {
            mockMvc.perform(delete("/reviews/101"))
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Удаление лайка отзыва")
        void methodDelete_ReviewDeleteLike() throws Exception {
            mockMvc.perform(delete("/reviews/101/like/101"))
                    .andExpect(status().isOk())
            ;
        }

        @Test
        @DisplayName("Удаление дизлайка отзыва")
        void methodDelete_ReviewDeleteDislike() throws Exception {
            mockMvc.perform(delete("/reviews/101/dislike/102"))
                    .andExpect(status().isOk())
            ;
        }
    }
}