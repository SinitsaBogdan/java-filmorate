package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalLikeFilm {

    @Positive
    private Long filmId;

    @Positive
    private Long userId;
}
