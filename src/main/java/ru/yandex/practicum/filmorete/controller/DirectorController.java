package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.service.ServiceDirector;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/directors")
@RestController
public class DirectorController {

    private final ServiceDirector serviceDirector;

    private DirectorController(ServiceDirector serviceDirector) {
        this.serviceDirector = serviceDirector;
    }

    /**
     * Запрос всех режиссёров.
     */
    @GetMapping()
    public List<Director> getAllDirectors() {
        log.info("GET-запрос: получение списка всех режиссёров.");
        return serviceDirector.getAllDirector();
    }

    /**
     * Добавление нового режиссёра.
     */
    @PostMapping
    public Director create(@Valid @RequestBody Director director) {
        log.info("POST-запрос: добавление нового режиссёра: {}.", director);
        return serviceDirector.add(director);
    }

    /**
     * Обновление параметров режиссёра.
     */
    @PutMapping()
    public Director update(@Valid @RequestBody Director director) {
        log.info("Put-запрос: обновление существующего режиссёра: {}.", director);
        return serviceDirector.update(director);
    }

    /**
     * Удаление всех режиссёров.
     */
    @DeleteMapping()
    public void removeAll() {
        log.info("Delete-запрос: удаление всех режиссёров.");
        serviceDirector.deleteAll();
    }

    /**
     * Запрос режиссёра по идентификатору.
     */
    @GetMapping("/{directorId}")
    public Director getSearchId(@PathVariable Long directorId) {
        log.info("Get-запрос: получение режиссёра по id {}.", directorId);
        return serviceDirector.getDirectorSearchId(directorId);
    }

    /**
     * Удаление режиссёра по идентификатору.
     */
    @DeleteMapping("/{directorId}")
    public void removeSearchId(@PathVariable Long directorId) {
        log.info("Delete-запрос: удаление режиссёра по id {}.", directorId);
        serviceDirector.deleteSearchId(directorId);
    }

    /**
     * Удаление режиссёра по имени.
     */
    @DeleteMapping("/name/{directorName}")
    public void removeSearchId(@PathVariable String directorName) {
        log.info("Delete-запрос: удаление режиссёра по имени {}.", directorName);
        serviceDirector.deleteSearchName(directorName);
    }
}
