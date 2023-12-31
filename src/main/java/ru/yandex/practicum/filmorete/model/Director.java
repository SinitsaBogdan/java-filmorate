package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Director {

    @Positive
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    public Director(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
