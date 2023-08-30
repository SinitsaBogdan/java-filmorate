package ru.yandex.practicum.filmorete.controller.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventResponse {
    Long timestamp;
    Long userId;
    String eventType;
    String operation;
    Long eventId;
    Long entityId;
}
