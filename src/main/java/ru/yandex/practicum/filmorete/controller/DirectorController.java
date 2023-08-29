package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.model.Director;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/directors")
@RestController
public class DirectorController {

    /**
     * NEW!!!
     * Получение режиссёра по идентификатору.
     */
    @GetMapping("/{directorId}")
    public void getSearchId(@PathVariable Long directorId) {

    }

    /**
     * NEW!!!
     * Получение всех режиссёров.
     */
    @GetMapping()
    public void getAllDirectors() {

    }

    /**
     * NEW!!!
     * Добавление нового режиссёра.
     */
    @PostMapping
    public void create(/* @Valid @RequestBody Director director */) {

    }

    /**
     * NEW!!!
     * Удаление режиссёра по идентификатору.
     */
    @DeleteMapping("/{directorId}")
    public void removeSearchId(@PathVariable Long directorId) {

    }

    /**
     * NEW!!!
     * Изменение параметров режиссёра.
     */
    @PutMapping()
    public void update(@Valid @RequestBody Director director) {

    }
}