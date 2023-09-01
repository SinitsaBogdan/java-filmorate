package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
public class Mpa {

    @Positive
    private Integer id;

    @NotBlank
    private String name;

    private String description;
}
