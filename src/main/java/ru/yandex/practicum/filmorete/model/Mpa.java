package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Mpa {

    @Positive
    private Integer id;

    @NotBlank
    private String name;

    private String description;
}
