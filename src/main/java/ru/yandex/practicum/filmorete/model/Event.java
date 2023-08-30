package ru.yandex.practicum.filmorete.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    
    @Positive
    private Long id;

    @Positive
    private Long userId;

    @Positive
    private Long typeId;

    @NotNull
    private final LocalDateTime releaseDate;

    @Positive
    private Long entityId;
}
