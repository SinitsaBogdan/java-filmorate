package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class TotalLikeFilm {

    @Positive
    private Long filmId;

    @Positive
    private Long userId;
}
