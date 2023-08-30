package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.service.ServiceDirectors;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/directors")
@RestController
public class DirectorController {

    private final ServiceDirectors serviceDirectors;

    public DirectorController(ServiceDirectors serviceDirectors) {
        this.serviceDirectors = serviceDirectors;
    }

    /**
     * NEW!!!
     * Получение режиссёра по идентификатору.
     */
    @GetMapping("/{directorId}")
    public Director getSearchId(@PathVariable Long directorId) {
        return serviceDirectors.getDirectorSearchId(directorId);
    }

    /**
     * NEW!!!
     * Получение всех режиссёров.
     */
    @GetMapping()
    public List<Director> getAllDirectors() {
        return serviceDirectors.getAllDirector();
    }

    /**
     * NEW!!!
     * Добавление нового режиссёра.
     */
    @PostMapping
    public Director create(@Valid @RequestBody String directorName) {
        return serviceDirectors.add(directorName);
    }

    /**
     * NEW!!!
     * Удаление режиссёра по идентификатору.
     */
    @DeleteMapping("/{directorId}")
    public void removeSearchId(@PathVariable Long directorId) {
        serviceDirectors.deleteSearchId(directorId);
    }

    /**
     * NEW!!!
     * Изменение параметров режиссёра.
     */
    @PutMapping()
    public Director update(@Valid @RequestBody Director director) {
        return serviceDirectors.update(director);
    }
}
