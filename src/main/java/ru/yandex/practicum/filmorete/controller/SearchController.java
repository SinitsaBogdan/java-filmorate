package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequestMapping("/films/search")
@RestController
public class SearchController {


    /**
     * NEW!!!
     * Поиск фильмов по режиссеру и/или названию.
     * */
    @GetMapping()
    public void getFilmsBySearchParam(@RequestParam String query, @RequestParam List<String> by) {


    }
}
