package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalFilmLike {

    private Long filmId;

    private Long userId;
}
