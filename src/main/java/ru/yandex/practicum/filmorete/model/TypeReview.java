package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
public class TypeReview {

    @Positive
    private final Integer id;

    @NotBlank
    private final String name;
}
