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
        log.info("GET [http://localhost:8080/directors] : Запрос списка всех режиссёров");
        return serviceDirector.getAllDirector();
    }

    /**
     * Добавление нового режиссёра.
     */
    @PostMapping
    public Director create(@Valid @RequestBody Director director) {
        log.info("POST [http://localhost:8080/directors] : Запрос на добавление нового режиссёра {}", director);
        return serviceDirector.add(director);
    }

    /**
     * Обновление параметров режиссёра.
     */
    @PutMapping()
    public Director update(@Valid @RequestBody Director director) {
        log.info("PUT [http://localhost:8080/directors] : Запрос на обновление существующего режиссёра: {}", director);
        return serviceDirector.update(director);
    }

    /**
     * Удаление всех режиссёров.
     */
    @DeleteMapping()
    public void removeAll() {
        log.info("DELETE [http://localhost:8080/directors] : Запрос на удаление всех режиссёров");
        serviceDirector.deleteAll();
    }

    /**
     * Запрос режиссёра по идентификатору.
     */
    @GetMapping("/{directorId}")
    public Director getSearchId(@PathVariable Long directorId) {
        log.info("GET [http://localhost:8080/directors/{}] : Запрос на получение режиссёра по id", directorId);
        return serviceDirector.getDirectorSearchId(directorId);
    }

    /**
     * Удаление режиссёра по идентификатору.
     */
    @DeleteMapping("/{directorId}")
    public void removeSearchId(@PathVariable Long directorId) {
        log.info("DELETE [http://localhost:8080/directors/{}]: Запрос на удаление режиссёра по id", directorId);
        serviceDirector.deleteSearchId(directorId);
    }

    /**
     * Удаление режиссёра по имени.
     */
    @DeleteMapping("/name/{directorName}")
    public void removeSearchId(@PathVariable String directorName) {
        log.info("DELETE [http://localhost:8080/directors/name/{}] : Запрос на удаление режиссёра по имени", directorName);
        serviceDirector.deleteSearchName(directorName);
    }
}
