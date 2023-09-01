package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundReviewStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundUserStorage;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.model.TotalLikeReview;
import ru.yandex.practicum.filmorete.sql.dao.*;

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
        return reviewDao.findAllIsCount(count);
    }

    public List<Review> getAllReviewIsFilmId(Long filmId, Integer count) {
        return reviewDao.findAllFilmIdAndIsCount(filmId, count);
    }

    /**
     * Запрос отзыва по ID [ REVIEWS ].
     */
    public Review getReviewSearchId(Long reviewId) {
        Optional<Review> result = reviewDao.findByReviewId(reviewId);
        if (result.isPresent()) return result.get();
        else throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
    }

    /**
     * Добавление нового отзыва [ REVIEWS ].
     */
    public Review add(@NotNull Review review) {
        if (userDao.findUser(review.getUserId()).isEmpty())
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        if (filmDao.findFilm(review.getFilmId()).isEmpty())
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        Long reviewId = reviewDao.insert(review.getContent(), review.getIsPositive(), review.getUserId(), review.getFilmId());
        eventsDao.insert(EventType.REVIEW, EventOperation.ADD, review.getUserId(), reviewId);
        return reviewDao.findByReviewId(reviewId).get();
    }

    /**
     * Обновление существующего отзыва [ REVIEWS ].
     */
    public Review update(@NotNull Review review) {
        if (userDao.findUser(review.getUserId()).isEmpty())
            throw new ExceptionNotFoundUserStorage(VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS);
        if (filmDao.findFilm(review.getFilmId()).isEmpty())
            throw new ExceptionNotFoundFilmStorage(VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS);
        if (reviewDao.findByReviewId(review.getReviewId()).isEmpty())
            throw new ExceptionNotFoundReviewStorage(SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS);
        reviewDao.update(review.getReviewId(), review.getContent(), review.getIsPositive());
        eventsDao.insert(EventType.REVIEW, EventOperation.UPDATE, review.getUserId(), review.getReviewId());
        return reviewDao.findByReviewId(review.getReviewId()).get();
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
        Optional<Event> event = eventsDao.findByEventTypeAndEntityId(EventType.REVIEW, reviewId);
        if (event.isPresent()) {
            eventsDao.insert(EventType.REVIEW, EventOperation.REMOVE, event.get().getUserId(), reviewId);
            reviewDao.delete(reviewId);
        }
    }

    /**
     * Добавление нового лайка отзыва [ TOTAL_LIKE_REVIEWS ].
     */
    public void add(@NotNull TotalLikeReview reviewLike) {
        totalReviewLikeDao.insert(reviewLike.getReviewId(), reviewLike.getUserId(), reviewLike.isTypeLike());
        reviewDao.recalculationPositive(reviewLike.getReviewId());
        eventsDao.insert(EventType.LIKE, EventOperation.ADD, reviewLike.getUserId(), reviewLike.getReviewId());
    }

    /**
     * Удаление лайка отзыва по ID отзыва и ID пользователя [ TOTAL_LIKE_REVIEWS ].
     */
    public void deleteReviewLike(Long reviewLikeId, Long userId) {
        totalReviewLikeDao.delete(reviewLikeId, userId);
        reviewDao.recalculationPositive(reviewLikeId);
        eventsDao.insert(EventType.LIKE, EventOperation.REMOVE, userId, reviewLikeId);
    }
}
