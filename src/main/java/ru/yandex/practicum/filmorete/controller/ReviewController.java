package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/reviews")
@RestController
public class ReviewController {


    /**
     * NEW!!!
     * Добавление нового отзыва.
     * */
    @PostMapping
    public void create(/*@Valid @RequestBody Review review*/) {

    }

    /**
     * NEW!!!
     * Редактирование уже имеющегося отзыва.
     * */
    @PutMapping
    public void update(/*@Valid @RequestBody Review review*/) {

    }

    /**
     * NEW!!!
     * Удаление уже имеющегося отзыва.
     * */
    @DeleteMapping("/{reviewId}")
    public void removeSearchId(@PathVariable Long reviewId) {

    }

    /**
     * NEW!!!
     * Получение отзыва по идентификатору.
     * */
    @GetMapping("/{reviewId}")
    public void getSearchId(@PathVariable Long reviewId) {

    }

    /**
     * NEW!!!
     * Получение всех отзывов по идентификатору фильма.
     * */
    @GetMapping()
    public void getAllFilmReviews(@RequestParam Long filmId, @RequestParam(defaultValue = "10") Integer count) {

    }

    /**
     * NEW!!!
     * Пользователь ставит лайк отзыву.
     * */
    @PutMapping("/{reviewId}/like/{userId}")
    public void addLikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {

    }

    /**
     * NEW!!!
     * Пользователь ставит дизлайк отзыву.
     * */
    @PutMapping("/{reviewId}/dislike/{userId}")
    public void addDislikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {

    }

    /**
     * NEW!!!
     * Пользователь удаляет лайк отзыву.
     * */
    @DeleteMapping("/{reviewId}/like/{userId}")
    public void deleteLikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {

    }

    /**
     * NEW!!!
     * Пользователь удаляет дизлайк отзыву.
     * */
    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void deleteDislikeReview(@PathVariable Long reviewId, @PathVariable Long userId) {

    }
}
