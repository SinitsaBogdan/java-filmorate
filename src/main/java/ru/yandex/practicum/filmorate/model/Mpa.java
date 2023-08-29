package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mpa {
    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @Positive
    private String description;
}
