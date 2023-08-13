package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.service.ServiceGenre;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/genres")
@RestController
public class GenreController {

    private final ServiceGenre serviceGenre;

    public GenreController(ServiceGenre serviceGenre) {
        this.serviceGenre = serviceGenre;
    }

    /**
     * Запрос всех записей
     */
    @GetMapping
    public List<Genre> getAllGenres() {
        return serviceGenre.getAllGenres();
    }

    /**
     * Запрос записи по id
     */
    @GetMapping("/{genreId}")
    public Genre getGenresSearchId(@PathVariable Integer genreId) {
        return serviceGenre.getGenresSearchId(genreId);
    }

    /**
     * Добавление новой записи
     */
    @PostMapping
    public void post(@Valid @RequestBody Genre genre) {
        serviceGenre.add(genre);
    }

    /**
     * Обновление существующей записи
     */
    @PutMapping
    public void put(@Valid @RequestBody Genre genre) {
        serviceGenre.update(genre);
    }

    /**
     * Очистка хранилища
     */
    @DeleteMapping
    public void deleteAll() {
        serviceGenre.deleteAll();
    }

    /**
     * Удаление записи по id
     */
    @DeleteMapping("/{genreId}")
    public void deleteMpaSearchId(@PathVariable Integer genreId) {
        serviceGenre.deleteSearchId(genreId);
    }
}

