package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.model.Review;
import ru.yandex.practicum.filmorete.model.TotalLikeReview;
import ru.yandex.practicum.filmorete.service.ServiceReview;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/reviews")
@RestController
public class ReviewController {

    private final ServiceReview serviceReview;

    public ReviewController(ServiceReview serviceReview) {
        this.serviceReview = serviceReview;
    }

    /**
     * Получение всех отзывов по идентификатору фильма.
     * */
    @GetMapping
    public List<Review> getAllReviewsFilm(
            @RequestParam(required = false) Long filmId,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        if (filmId == null && count == 10) return serviceReview.getAllReview(10); //логи внутри
        else if (filmId == null) return serviceReview.getAllReview(count);
        else return serviceReview.getAllReviewIsFilmId(filmId, count);
    }

    /**
     * Добавление нового отзыва.
     * */
    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        log.info("Post-запрос: добавление нового отзыва: {}.", review);
        return serviceReview.add(review);
    }

    /**
     * Редактирование уже имеющегося отзыва.
     * */
    @PutMapping
    public Review update(@Valid @RequestBody Review review) {
        log.info("Put-запрос: редактирование имеющегося отзыва: {}.", review);
        return serviceReview.update(review);
    }

    /**
     * Удаление всех отзывов.
     * */
    @DeleteMapping
    public void removeAll() {
        log.info("Delete-запрос: удаление всех отзывов.");
        serviceReview.delete();
    }

    /**
     * Запрос отзыва по идентификатору.
     * */
    @GetMapping("/{reviewId}")
    public Review getSearchId(@PathVariable Long reviewId) {
        log.info("Get-запрос: получение отзыва по id {}.", reviewId);
        return serviceReview.getReviewSearchId(reviewId);
    }

    /**
     * Удаление уже имеющегося отзыва по ID.
     * */
    @DeleteMapping("/{reviewId}")
    public void removeSearchId(@PathVariable Long reviewId) {
        log.info("Delete-запрос: удаление отзыва по id {}.", reviewId);
        serviceReview.delete(reviewId);
    }

    /**
     * Пользователь ставит лайк отзыву.
     * */
    @PutMapping("/{reviewId}/like/{userId}")
    public void addLikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        log.info("Put-запрос: пользователь {} ставит лайк отзыву по id {}.", userId, reviewId);
        serviceReview.add(TotalLikeReview.builder().reviewId(reviewId).typeLike(true).userId(userId).build());
    }

    /**
     * Пользователь удаляет лайк у отзыва.
     * */
    @DeleteMapping("/{reviewId}/like/{userId}")
    public void deleteLikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        log.info("Delete-запрос: пользователь {} удаляет лайк у отзыва по id {}.", userId, reviewId);
        serviceReview.deleteReviewLike(reviewId, userId);
    }

    /**
     * Пользователь ставит дизлайк отзыву.
     * */
    @PutMapping("/{reviewId}/dislike/{userId}")
    public void addDislikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        log.info("Put-запрос: пользователь {} ставит дизлайк отзыву по id {}.", userId, reviewId);
        serviceReview.add(TotalLikeReview.builder().reviewId(reviewId).typeLike(false).userId(userId).build());
    }

    /**
     * Пользователь удаляет дизлайк у отзыва.
     * */
    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void deleteDislikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        log.info("Delete-запрос: пользователь {} удаляет дизлайк у отзыва по id {}.", userId, reviewId);
        serviceReview.deleteReviewLike(reviewId, userId);
    }
}
