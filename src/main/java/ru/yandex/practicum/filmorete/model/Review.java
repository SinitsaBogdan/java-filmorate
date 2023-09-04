package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {

    @Positive
    private Long reviewId;

    @NotBlank
    @Size(max = 200)
    private String content;

    @NotNull
    private Boolean isPositive;

    @NotNull
    private Long userId;

    @NotNull
    private Long filmId;

    @Builder.Default
    private Integer useful = 0;
}
