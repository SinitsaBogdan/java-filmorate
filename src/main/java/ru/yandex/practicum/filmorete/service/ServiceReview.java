package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundReviewStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.model.TotalLikeReview;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.sql.dao.*;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.message.ReviewErrorMessage.SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.message.ValidFilmErrorMessage.VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.message.UserErrorMessage.ERROR_USER_ID_NOT_IN_COLLECTIONS;

@Slf4j
@Service
public class ServiceReview {

    private final ReviewDao reviewDao;

    private final UserDao userDao;

    private final FilmDao filmDao;

    private final TotalLikeReviewDao totalReviewLikeDao;

    private final EventsDao eventsDao;

    private ServiceReview(ReviewDao reviewDao, UserDao userDao, FilmDao filmDao, TotalLikeReviewDao totalReviewLikeDao, EventsDao eventsDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.filmDao = filmDao;
        this.totalReviewLikeDao = totalReviewLikeDao;
        this.eventsDao = eventsDao;
    }

    /**
     * Запрос всех отзывов [ REVIEWS ].
     */
    public List<Review> getAllReview(Integer count) {
        log.info("GET-запрос: получение списка всех отзывов.");
        return reviewDao.findAllIsCount(count);
    }

    public List<Review> getAllReviewIsFilmId(Long filmId, Integer count) {
        log.info("GET-запрос: получение всех отзывов по идентификатору фильма {}.", filmId);
        return reviewDao.findAllIsFilmIdAndCount(filmId, count);
    }

    /**
     * Запрос отзыва по ID [ REVIEWS ].
     */
    public Review getReviewSearchId(Long reviewId) {
        Optional<Review> result = reviewDao.findByReviewId(reviewId);
        if (result.isEmpty()) throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        return result.get();
    }

    /**
     * Добавление нового отзыва [ REVIEWS ].
     */
    public Review add(@NotNull Review reviews) {
        Optional<User> optionalUser = userDao.findByRowId(reviews.getUserId());
        Optional<Film> optionalFilm = filmDao.findFilmById(reviews.getFilmId());
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(ERROR_USER_ID_NOT_IN_COLLECTIONS);
        if (optionalFilm.isEmpty()) throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        Long reviewId = reviewDao.insert(reviews.getContent(), reviews.getIsPositive(), reviews.getUserId(), reviews.getFilmId());
        eventsDao.insert(EventType.REVIEW, EventOperation.ADD, reviews.getUserId(), reviewId);
        return reviewDao.findByReviewId(reviewId).get();
    }

    /**
     * Обновление существующего отзыва [ REVIEWS ].
     */
    public Review update(@NotNull Review reviews) {
        Optional<Review> optional = reviewDao.findByReviewId(reviews.getReviewId());
        if (optional.isEmpty()) throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        reviewDao.update(reviews.getReviewId(), reviews.getContent(), reviews.getIsPositive());
        eventsDao.insert(EventType.REVIEW, EventOperation.UPDATE, optional.get().getUserId(), reviews.getReviewId());
        return reviewDao.findByReviewId(reviews.getReviewId()).get();
    }

    /**
     * Удаление всех отзывов [ REVIEWS ].
     */
    public void delete() {
        reviewDao.deleteAll();
    }

    /**
     * Удаление отзыва по ID [ REVIEWS ].
     */
    public void delete(Long reviewId) {
        Optional<Review> optional = reviewDao.findByReviewId(reviewId);
        if (optional.isEmpty()) throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        reviewDao.deleteAllIsReviewId(reviewId);
        eventsDao.insert(EventType.REVIEW, EventOperation.REMOVE, optional.get().getUserId(), reviewId);
    }

    /**
     * Добавление нового лайка отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void add(@NotNull TotalLikeReview reviewLike) {
        Optional<Review> optionalReview = reviewDao.findByReviewId(reviewLike.getReviewId());
        if (optionalReview.isEmpty()) throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        totalReviewLikeDao.insert(reviewLike.getReviewId(), reviewLike.getUserId(), reviewLike.isTypeLike());
        reviewDao.recalculationPositive(reviewLike.getReviewId());
    }

    /**
     * Удаление лайка отзыва по ID отзыва и ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public void deleteReviewLike(Long reviewLikeId, Long userId) {
        Optional<Review> optionalReview = reviewDao.findByReviewId(reviewLikeId);
        Optional<User> optionalUser = userDao.findByRowId(userId);
        if (optionalReview.isEmpty()) throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        if (optionalUser.isEmpty()) throw new ExceptionNotFoundUserStorage(ERROR_USER_ID_NOT_IN_COLLECTIONS);
        totalReviewLikeDao.deleteByReviewIdAndUserId(reviewLikeId, userId);
        reviewDao.recalculationPositive(reviewLikeId);
    }
}
