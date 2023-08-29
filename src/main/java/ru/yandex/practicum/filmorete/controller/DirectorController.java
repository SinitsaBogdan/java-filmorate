package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void update(/*@Valid @RequestBody Director director */) {

    }
}
