package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventType {
    
    @Positive
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;
}
