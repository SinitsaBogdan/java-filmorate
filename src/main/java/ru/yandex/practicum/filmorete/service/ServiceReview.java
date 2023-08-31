package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundReviewStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.model.TotalLikeReview;
import ru.yandex.practicum.filmorete.sql.dao.FilmDao;
import ru.yandex.practicum.filmorete.sql.dao.ReviewDao;
import ru.yandex.practicum.filmorete.sql.dao.TotalLikeReviewDao;
import ru.yandex.practicum.filmorete.sql.dao.UserDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceReview.SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidFilm.VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.MessageErrorValidUser.VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS;

@Service
public class ServiceReview {

    private final ReviewDao reviewDao;

    private final UserDao userDao;

    private final FilmDao filmDao;

    private final TotalLikeReviewDao totalReviewLikeDao;

    private ServiceReview(ReviewDao reviewDao, UserDao userDao, FilmDao filmDao, TotalLikeReviewDao totalReviewLikeDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.filmDao = filmDao;
        this.totalReviewLikeDao = totalReviewLikeDao;
    }

    /**
     * Запрос всех отзывов [ REVIEWS ].
     */
    public List<Review> getAllReview() {
        return reviewDao.findAll();
    }

    /**
     * Запрос всех отзывов по ID пользователя [ REVIEWS ].
     */
    public List<Review> getAllReviewIsUserId(Long userId) {
        return reviewDao.findAll(userId);
    }

    /**
     * Запрос всех отзывов по ID фильма [ REVIEWS ].
     */
    public List<Review> getAllReviewIsFilmId(Long filmId, Integer count) {
        return reviewDao.findAll(filmId, count);
    }

    /**
     * Запрос всех отзывов по типу отзыва [ REVIEWS ].
     */
    public List<Review> getAllReviewIsTypeId(Boolean isPositive) {
        return reviewDao.findAll(isPositive);
    }

    /**
     * Запрос всех отзывов по общей полезности [ REVIEWS ].
     */
    public List<Review> getAllReviewIsUseful(Integer useful) {
        return reviewDao.findByUseful(useful);
    }

    /**
     * Запрос отзыва по ID [ REVIEWS ].
     */
    public Review getReviewSearchId(Long reviewId) {
        Optional<Review> result = reviewDao.findById(reviewId);
        if (result.isPresent()) return result.get();
        else throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
    }

    /**
     * Добавление нового отзыва [ REVIEWS ].
     */
    public Review add(@NotNull Review review) {
        if (userDao.findUser(review.getUserId()).isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        if (filmDao.findFilm(review.getFilmId()).isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        Long reviewId = reviewDao.insert(review.getContent(), review.getIsPositive(), review.getUserId(), review.getFilmId());
        return reviewDao.findById(reviewId).get();
    }

    /**
     * Обновление существующего отзыва [ REVIEWS ].
     */
    public Review update(@NotNull Review review) {
        if (userDao.findUser(review.getUserId()).isEmpty()) throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        if (filmDao.findFilm(review.getFilmId()).isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        if (reviewDao.findById(review.getReviewId()).isEmpty()) throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        reviewDao.update(review.getReviewId(), review.getContent(), review.getIsPositive());
        return reviewDao.findById(review.getReviewId()).get();
    }

    /**
     * Удаление всех отзывов [ REVIEWS ].
     */
    public void delete() {
        reviewDao.delete();
    }

    /**
     * Удаление отзыва по ID [ REVIEWS ].
     */
    public void delete(Long reviewId) {
        reviewDao.delete(reviewId);
    }

    /**
     * Удаление отзывов по ID фильма [ REVIEWS ].
     */
    public void deleteAllSearchFilmId(Long filmId) {
        reviewDao.deleteAllFilmId(filmId);
    }

    /**
     * Удаление отзывов по ID пользователя [ REVIEWS ].
     */
    public void deleteAllSearchUserId(Long userId) {
        reviewDao.deleteAllUserId(userId);
    }

    /**
     * Запрос всех записей [ TOTAL_LIKE_REVIEWS ].
     */
    public List<TotalLikeReview> getAllTotalReviewLike() {
        return totalReviewLikeDao.findAll();
    }

    /**
     * Запрос всех записей по ID отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public List<TotalLikeReview> getAllTotalReviewLikeSearchReviewId(Long reviewId) {
        return totalReviewLikeDao.findAllByReviewId(reviewId);
    }

    /**
     * Запрос всех записей по ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public List<TotalLikeReview> getAllTotalReviewLikeSearchUserId(Long userId) {
        return totalReviewLikeDao.findAllByUserId(userId);
    }

    /**
     * Запрос всех записей по статусу оценки [ TOTAL_LIKE_REVIEWS ].
     */
    public List<TotalLikeReview> getAllTotalReviewLikeSearchIsPositive(Boolean isPositive) {
        return totalReviewLikeDao.findAllByIsPositive(isPositive);
    }

    /**
     * Добавление нового лайка отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void add(@NotNull TotalLikeReview reviewLike) {
        totalReviewLikeDao.insert(reviewLike.getReviewId(), reviewLike.getUserId(), reviewLike.isTypeLike());
        reviewDao.updateUseful(reviewLike.getReviewId());
    }

    /**
     * Обновление лайка отзыва по ID отзыва и ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public void updateReviewLike(@NotNull TotalLikeReview reviewLike) {
        totalReviewLikeDao.update(reviewLike.getReviewId(), reviewLike.getUserId(), reviewLike.isTypeLike());
    }

    /**
     * Удаление лайка отзыва по ID отзыва и ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public void deleteReviewLike(Long reviewLikeId, Long userId) {
        totalReviewLikeDao.delete(reviewLikeId, userId);
        reviewDao.updateUseful(reviewLikeId);
    }

    /**
     * Удаление всех лайков отзыва по ID отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void deleteAllReviewLikeSearchId(Long reviewLikeId) {
        totalReviewLikeDao.deleteAllReviewId(reviewLikeId);
        reviewDao.updateUseful(reviewLikeId);
    }
}
