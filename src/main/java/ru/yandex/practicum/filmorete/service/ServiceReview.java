package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.model.Reviews;
import ru.yandex.practicum.filmorete.model.TotalReviewLike;

import java.util.ArrayList;

@Service
public class ServiceReview {

//    private final ReviewsDao reviewsDao;
//    private final TotalReviewLikeDao totalReviewLikeDao;
//
//    private ServiceReview(ReviewsDao reviewsDao, TotalReviewLikeDao totalReviewLikeDao) {
//        this.reviewsDao = reviewsDao;
//        this.totalReviewLikeDao = totalReviewLikeDao;
//    }

    /**
     * NEW!!!
     * Запрос всех отзывов [ REVIEWS ].
     */
    public ArrayList<Reviews> getAllReview() {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по ID пользователя [ REVIEWS ].
     */
    public ArrayList<Reviews> getAllReviewIsUserId(Long userId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по ID фильма [ REVIEWS ].
     */
    public ArrayList<Reviews> getAllReviewIsFilmId(Long filmId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по типу отзыва [ REVIEWS ].
     */
    public ArrayList<Reviews> getAllReviewIsTypeId(Integer typeId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по типу оценки [ REVIEWS ].
     */
    public ArrayList<Reviews> getAllReviewIsEvaluationId(Integer evaluationId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех отзывов по общей полезности [ REVIEWS ].
     */
    public ArrayList<Reviews> getAllReviewIsUseful(Integer useful) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос отзыва по ID [ REVIEWS ].
     */
    public Reviews getReviewSearchId(Long reviewsId) {
        return Reviews.builder().build();
    }

    /**
     * NEW!!!
     * Добавление нового отзыва [ REVIEWS ].
     */
    public void add(@NotNull Reviews reviews) {
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
    public ArrayList<TotalReviewLike> getAllTotalReviewLike() {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех записей по ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalReviewLike> getAllTotalReviewLikeSearchUserId(Long userId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех записей по ID фильма [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalReviewLike> getAllTotalReviewLikeSearchFilmId(Long filmId) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Запрос всех записей по статусу оценки [ TOTAL_LIKE_REVIEWS ].
     */
    public ArrayList<TotalReviewLike> getAllTotalReviewLikeSearchFilmId(Boolean status) {
        return new ArrayList<>();
    }

    /**
     * NEW!!!
     * Добавление нового лайка отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void add(@NotNull TotalReviewLike reviewLike) {
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
