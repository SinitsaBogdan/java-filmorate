package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class TotalLikeReview {

    @Positive
    private Long reviewId;

    @Positive
    private Long userId;

    @NotNull
    private boolean typeLike;
}
