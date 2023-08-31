package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.model.TotalLikeReview;
import ru.yandex.practicum.filmorete.sql.dao.ReviewDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalLikeReviewDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceReview {

    private final ReviewDao reviewDao;
    private final TotalLikeReviewDao totalReviewLikeDao;

    private ServiceReview(ReviewDao reviewDao, TotalLikeReviewDao totalReviewLikeDao) {
        this.reviewDao = reviewDao;
        this.totalReviewLikeDao = totalReviewLikeDao;
    }

    /**
     * NEW!!!
     * Запрос всех отзывов [ REVIEWS ].
     */
    public List<Review> getAllReview() {
        return reviewDao.findAll();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по ID пользователя [ REVIEWS ].
     */
    public List<Review> getAllReviewIsUserId(Long userId) {
        return reviewDao.findReviewIsUserId(userId);

    }

    /**
     * NEW!!!
     * Запрос всех отзывов по ID фильма [ REVIEWS ].
     */
    public List<Review> getAllReviewIsFilmId(Long filmId) {
        return reviewDao.findIsFilmId(filmId);

    }

    /**
     * NEW!!!
     * Запрос всех отзывов по типу отзыва [ REVIEWS ].
     */
    public ArrayList<Review> getAllReviewIsTypeId(Integer typeId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по типу оценки [ REVIEWS ].
     */
    public ArrayList<Review> getAllReviewIsEvaluationId(Integer evaluationId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по общей полезности [ REVIEWS ].
     */
    public ArrayList<Review> getAllReviewIsUseful(Integer useful) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос отзыва по ID [ REVIEWS ].
     */
    public Review getReviewSearchId(Long reviewsId) {
        return Review.builder().build();
    }

    /**
     * NEW!!!
     * Добавление нового отзыва [ REVIEWS ].
     */
    public void add(@NotNull Review reviews) {
    }

    /**
     * NEW!!!
     * Обновление существующего отзыва [ REVIEWS ].
     */
    public void update(@NotNull Director director) {
    }

    /**
     * NEW!!!
     * Удаление всех отзывов [ REVIEWS ].
     */
    public void deleteAll() {
    }

    /**
     * NEW!!!
     * Удаление отзывов по ID фильма [ REVIEWS ].
     */
    public void deleteAllSearchFilmId(Long filmId) {
    }

    /**
     * NEW!!!
     * Удаление отзывов по ID пользователя [ REVIEWS ].
     */
    public void deleteAllSearchUserId(Long userId) {
    }

    /**
     * NEW!!!
     * Удаление отзыва по ID [ REVIEWS ].
     */
    public void deleteSearchId() {
    }

    /**
     * NEW!!!
     * Запрос всех записей [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalLikeReview> getAllTotalReviewLike() {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех записей по ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalLikeReview> getAllTotalReviewLikeSearchUserId(Long userId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех записей по ID фильма [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalLikeReview> getAllTotalReviewLikeSearchFilmId(Long filmId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех записей по статусу оценки [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalLikeReview> getAllTotalReviewLikeSearchFilmId(Boolean status) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Добавление нового лайка отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void add(@NotNull TotalLikeReview reviewLike) {
    }

    /**
     * NEW!!!
     * Удаление всех лайков отзыва по ID отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void deleteAllReviewLikeSearchId(Long reviewLikeId) {
    }

    /**
     * NEW!!!
     * Удаление всех лайков отзывов по ID пользователя [ REVIEWS ].
     */
    public void deleteReviewLikeSearchId(Long userId) {
    }

    /**
     * NEW!!!
     * Удаление лайка отзыва по ID отзыва и ID пользователя [ REVIEWS ].
     */
    public void deleteReviewLikeSearchReviewLikeIdAndUserId(Long reviewLikeId, Long userId) {
    }
}
