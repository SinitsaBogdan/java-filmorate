package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    
    @Positive
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String content;

    private boolean isPositive;

    @Positive
    private Long userId;

    @Positive
    private Long filmId;

    @Positive
    private Long typeId;

    @Positive
    private Long evaluationId;

    @Positive
    private Integer useful;
}
