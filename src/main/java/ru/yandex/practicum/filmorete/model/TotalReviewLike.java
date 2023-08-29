package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class TotalReviewLike {

    @Positive
    private Long reviewId;

    @Positive
    private Long userId;

    private boolean typeLike;
}
