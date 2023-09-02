package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class Event {

    @Positive
    private Long eventId;

    private Long timestamp;

    @Positive
    private Long userId;

    @NotNull
    private EventType eventType;

    @NotNull
    private EventOperation operation;

    @Positive
    private Long entityId;
}
