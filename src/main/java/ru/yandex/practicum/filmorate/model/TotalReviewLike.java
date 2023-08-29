package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Positive;

public class TotalReviewLike {

    @Positive
    private Long reviewId;

    @Positive
    private Long userId;

    private boolean typeLike;
}
