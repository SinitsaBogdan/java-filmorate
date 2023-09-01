package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
@Builder
public class Event {

    @Positive
    private Long eventId;

    @Builder.Default
    private Long timestamp = Instant.now().getEpochSecond();

    @Positive
    private Long userId;

    @NotNull
    private EventType eventType;

    @NotNull
    private EventOperation operation;

    @Positive
    private Long entityId;

    public Event(Long eventId, Long timestamp, Long userId, EventType eventType, EventOperation operation, Long entityId) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.eventType = eventType;
        this.operation = operation;
        this.entityId = entityId;
    }
}
