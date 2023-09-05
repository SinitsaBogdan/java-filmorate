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
        log.info("   GET [http://localhost:8080/genres] : Запрос списка всех жанров");
        return serviceGenre.getAllGenres();
    }

    /**
     * Добавление новой записи
     */
    @PostMapping
    public void post(@Valid @RequestBody Genre genre) {
        log.info("  POST [http://localhost:8080/genres] : Запрос добавление нового жанра {}", genre);
        serviceGenre.add(genre);
    }

    /**
     * Очистка хранилища
     */
    @DeleteMapping
    public void deleteAll() {
        log.info("DELETE [http://localhost:8080/genres] : Запрос удаления всех жанров");
        serviceGenre.deleteAll();
    }

    /**
     * Обновление существующей записи
     */
    @PutMapping
    public void put(@Valid @RequestBody Genre genre) {
        log.info("   PUT [http://localhost:8080/genres] : Запрос обновления существующего жанра {}", genre);
        serviceGenre.update(genre);
    }

    /**
     * Запрос записи по id
     */
    @GetMapping("/{genreId}")
    public Genre getGenresSearchId(@PathVariable Integer genreId) {
        log.info("   GET [http://localhost:8080/genres/{}] : Запрос существующего жанра по id", genreId);
        return serviceGenre.getGenresSearchId(genreId);
    }

    /**
     * Удаление записи по id
     */
    @DeleteMapping("/{genreId}")
    public void deleteMpaSearchId(@PathVariable Integer genreId) {
        log.info("DELETE [http://localhost:8080/genres/{}] : Запрос удаления жанра по id", genreId);
        serviceGenre.deleteSearchId(genreId);
    }
}

