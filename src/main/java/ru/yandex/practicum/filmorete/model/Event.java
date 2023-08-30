package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Builder
public class Event {

    @Positive
    private Long id;

    @Positive
    private Long typeId;

    @NotNull
    private final LocalDateTime releaseDate = LocalDateTime.now();

    Long timestamp;

    @Positive
    Long userId;

    String eventType;

    String operation;

    @Positive
    Long eventId;

    @Positive
    Long entityId;
}
