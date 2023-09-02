package ru.yandex.practicum.filmorete.sql.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ReviewsDaoImplTest {

    private final RosterMpaDao enumMpaDao;
    private final ReviewDao reviewDao;
    private final FilmDao filmDao;
    private final UserDao userDao;
    private final TotalLikeReviewDao totalLikeReviewDao;

    @BeforeEach
    void setUp() {
        enumMpaDao.delete();
        filmDao.deleteAll();
        userDao.deleteAll();
        reviewDao.deleteAll();
        totalLikeReviewDao.delete();

        enumMpaDao.insert(1, "G", "Описание 2");
        filmDao.insert(100L, 1, "Фильм 1", "", LocalDate.of(2005, 1, 1), 90);
        filmDao.insert(101L, 1, "Фильм 2", "", LocalDate.of(2005, 1, 1), 90);
        userDao.insert(100L, "Максим", LocalDate.of(1895, 5, 24), "Maxim", "maxim@mail.ru");
        userDao.insert(101L, "Максим", LocalDate.of(1895, 5, 24), "Maxim2", "maxim2@mail.ru");
        userDao.insert(102L, "Максим", LocalDate.of(1895, 5, 24), "Maxim3", "maxim3@mail.ru");
        reviewDao.insert(100L, "Содержание отзыва 1", true, 100L, 100L);
        reviewDao.insert(101L, "Содержание отзыва 2", true, 101L, 100L);
    }

    @Test
    @DisplayName("findAll()")
    public void findAll() {
        List<Review> result = reviewDao.findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findAll(userId)")
    public void findAll__UserId() {
        List<Review> result = reviewDao.findAll(100L);
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("findAll(isPositive)")
    public void findAll__IsPositive() {
        List<Review> result;
        result = reviewDao.findAll(true);
        assertEquals(result.size(), 2);

        result = reviewDao.findAll(false);
        assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("findAll(filmId, count)")
    public void findAll__FilmId_And_Count() {
        List<Review> result;
        result = reviewDao.findAllIsCount(5);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findByUseful(useful)")
    public void findAll__Useful() {
        List<Review> result;
        result = reviewDao.findAllIsUseful(0);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("findByReviewId(reviewId)")
    public void findByReviewId() {
        Optional<Review> result;
        result = reviewDao.findByReviewId(100L);
        assertTrue(result.isPresent());

        Review review = Review.builder().reviewId(100L).content("Содержание отзыва 1").isPositive(true).userId(100L).filmId(100L).build();
        assertEquals(result.get(), review);
    }

    @Test
    @DisplayName("insert(id, content, isPositive, userId, filmId)")
    public void insert__All() {
        reviewDao.insert(200L, "Содержание отзыва 3", true, 100L, 100L);
        assertEquals(reviewDao.findAll().size(), 3);
    }

    @Test
    @DisplayName("insert(content, isPositive, userId, filmId)")
    public void insert__Content_IsPositive_UserId_FilmId() {
        reviewDao.insert("Содержание отзыва 3", true, 100L, 100L);
        assertEquals(reviewDao.findAll().size(), 3);
    }

    @Test
    @DisplayName("update(content, isPositive, userId, filmId)")
    public void update__Id_Content_IsPositive_UserId_FilmId() {
        reviewDao.update(100L, "Обновление", true);
        Optional<Review> result = reviewDao.findByReviewId(100L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getContent(), "Обновление");
        assertEquals(result.get().getIsPositive(), true);
        assertEquals(result.get().getUserId(), 100L);
        assertEquals(result.get().getFilmId(), 100L);
    }

    @Test
    @DisplayName("recalculationPositive(id)")
    public void recalculationPositive__Id() {
        Optional<Review> result;
        result = reviewDao.findByReviewId(100L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getUseful(), 0);

        totalLikeReviewDao.insert(100L, 101L, false);
        reviewDao.recalculationPositive(100L);

        result = reviewDao.findByReviewId(100L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getUseful(), -1);

        totalLikeReviewDao.insert(100L, 102L, true);
        reviewDao.recalculationPositive(100L);

        result = reviewDao.findByReviewId(100L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getUseful(), 0);
    }

    @Test
    @DisplayName("delete(reviewId)")
    public void delete__ReviewId() {
        reviewDao.deleteAllIsReviewId(100L);
        assertEquals(reviewDao.findAll().size(), 1);
    }

    @Test
    @DisplayName("deleteAll()")
    public void deleteAll() {
        reviewDao.deleteAll();
        assertEquals(reviewDao.findAll().size(), 0);
    }

    @Test
    @DisplayName("deleteAllUserId(userId)")
    public void deleteAllUserId__userId() {
        reviewDao.deleteAllIsUserId(100L);
        assertEquals(reviewDao.findAll().size(), 1);
    }

    @Test
    @DisplayName("deleteAllFilmId(filmId)")
    public void deleteAllFilmId__filmId() {
        reviewDao.deleteAllIsFilmId(100L);
        assertEquals(reviewDao.findAll().size(), 0);
    }
}