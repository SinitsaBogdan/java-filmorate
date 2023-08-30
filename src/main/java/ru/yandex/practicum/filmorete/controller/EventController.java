package ru.yandex.practicum.filmorete.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorete.controller.response.EventResponse;
import ru.yandex.practicum.filmorete.service.ServiceEvent;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class EventController {
    private final ServiceEvent serviceEvent;

    @GetMapping("{id}/feed")
    public ResponseEntity<List<EventResponse>> getEvents(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceEvent.getAllEventByUserId(id));
    }


}
