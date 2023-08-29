package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalDirectorFilm {
    
    @Positive
    private Long filmId;

    @Positive
    private Long directorId;
}
