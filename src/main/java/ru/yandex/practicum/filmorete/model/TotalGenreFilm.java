package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class TotalGenreFilm {

    @Positive
    private Long filmId;

    @Positive
    private Long genreId;
}
