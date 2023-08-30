package ru.yandex.practicum.filmorete.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.controller.response.EventResponse;
import ru.yandex.practicum.filmorete.model.Event;
import ru.yandex.practicum.filmorete.model.EventType;
import ru.yandex.practicum.filmorete.sql.dao.EventsDao;
import ru.yandex.practicum.filmorete.sql.dao.RosterEventTypeDao;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ServiceEvent {

    private final EventsDao eventsDao;
    private final RosterEventTypeDao rosterEventTypeDao;

    /**
     * NEW!!!
     * Запрос всех событий из таблицы EVENTS по USER_ID [ EVENTS ].
     */
    public List<EventResponse> getAllEventByUserId(Long userId) {
        List<Event> events = eventsDao.findAllByUserId(userId);
        List<EventType> eventTypes = rosterEventTypeDao.findAll();
        return events.stream()
                .map(event -> mappedEventToEventResponse(event, eventTypes))
                .collect(Collectors.toList());
    }

    private EventResponse mappedEventToEventResponse(Event event, List<EventType> eventTypes) {
        EventType eventType = eventTypes.stream()
                .filter(e -> e.getId().equals(event.getTypeId()))
                .findFirst()
                .orElseThrow();

        String[] typeAndOperation = eventType.getName().split(" ");

        return EventResponse.builder()
                .timestamp(Timestamp.valueOf(event.getReleaseDate()).getTime())
                .userId(event.getUserId())
                .eventType(typeAndOperation[1])
                .operation(typeAndOperation[0])
                .entityId(event.getEntityId())
                .eventId(event.getId())
                .build();
    }

}
