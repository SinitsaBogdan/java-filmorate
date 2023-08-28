package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/users/{userId}/feed")
@RestController
public class FeedController {

    /**
     * NEW!!!
     * Возвращает ленту событий пользователя.
     * */
    @GetMapping()
    public void getFeed(@PathVariable Long userId) {

    }
}
