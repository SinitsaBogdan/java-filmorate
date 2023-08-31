package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalFilmLike {

    @Positive
    private Long filmId;

    @Positive
    private Long userId;

    @Positive
    @Builder.Default
    private Integer estimation = 0;
}
