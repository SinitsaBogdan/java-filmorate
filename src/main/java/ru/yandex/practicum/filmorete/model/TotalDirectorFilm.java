package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TotalDirectorFilm {
    
    @Positive
    private Long filmId;

    @Positive
    private Long directorId;
}
