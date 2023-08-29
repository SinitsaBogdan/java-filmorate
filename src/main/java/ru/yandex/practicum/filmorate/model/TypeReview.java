package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeReview {
    
    @Positive
    private final Integer id;

    @NotBlank
    private final String name;
}
