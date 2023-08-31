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
    @GetMapping()
    public List<Review> getAllReviewsFilm(@RequestParam(defaultValue = "") Long filmId, @RequestParam(defaultValue = "10") Integer count) {
        if (filmId != null) return serviceReview.getAllReviewIsFilmId(filmId, count);
        else return serviceReview.getAllReview();
    }

    /**
     * Запрос отзыва по идентификатору.
     * */
    @GetMapping("/{reviewId}")
    public Review getSearchId(@PathVariable Long reviewId) {
        return serviceReview.getReviewSearchId(reviewId);
    }

    /**
     * Добавление нового отзыва.
     * */
    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        return serviceReview.add(review);
    }

    /**
     * Редактирование уже имеющегося отзыва.
     * */
    @PutMapping
    public Review update(@Valid @RequestBody Review review) {
        return serviceReview.update(review);
    }

    /**
     * Удаление всех отзывов.
     * */
    @DeleteMapping
    public void removeAll() {
        serviceReview.delete();
    }

    /**
     * Удаление уже имеющегося отзыва по ID.
     * */
    @DeleteMapping("/{reviewId}")
    public void removeSearchId(@PathVariable Long reviewId) {
        serviceReview.delete(reviewId);
    }

    /**
     * Пользователь ставит лайк отзыву.
     * */
    @PutMapping("/{reviewId}/like/{userId}")
    public void addLikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        serviceReview.add(TotalLikeReview.builder().reviewId(reviewId).typeLike(true).userId(userId).build());
    }

    /**
     * Пользователь ставит дизлайк отзыву.
     * */
    @PutMapping("/{reviewId}/dislike/{userId}")
    public void addDislikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        serviceReview.add(TotalLikeReview.builder().reviewId(reviewId).typeLike(false).userId(userId).build());
        // TODO Система изменения рейтинга у отзыва
    }

    /**
     * Пользователь удаляет оценку у отзыва.
     * */
    @DeleteMapping("/{reviewId}/like/{userId}")
    public void deleteLikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        serviceReview.deleteReviewLike(reviewId, userId);
    }

    /**
     * Пользователь удаляет дизлайк отзыву.
     * */
    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void deleteDislikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        serviceReview.deleteReviewLike(reviewId, userId);
    }
}
