package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
public class EventType {

    @Positive
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;
}
