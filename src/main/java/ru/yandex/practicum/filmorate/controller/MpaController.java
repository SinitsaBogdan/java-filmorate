package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.ServiceMpa;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/mpa")
@RestController
public class MpaController {

    private final ServiceMpa serviceMpa;

    public MpaController(ServiceMpa serviceMpa) {
        this.serviceMpa = serviceMpa;
    }

    /**
     * Запрос всего списка записей
     */
    @GetMapping
    public List<Mpa> getAll() {
        return serviceMpa.getAll();
    }

    /**
     * Запрос записи по id
     */
    @GetMapping("/{mpaId}")
    public Mpa getSearchId(@PathVariable Integer mpaId) {
        return serviceMpa.getSearchId(mpaId);
    }

    /**
     * Добавление новой записи
     */
    @PostMapping
    public void post(@Valid @RequestBody Mpa mpa) {
        serviceMpa.add(mpa);
    }

    /**
     * Обновление существующей записи
     */
    @PutMapping
    public void put(@Valid @RequestBody Mpa mpa) {
        serviceMpa.update(mpa);
    }

    /**
     * Удаление всех записей
     */
    @DeleteMapping
    public void deleteAll() {
        serviceMpa.deleteAll();
    }

    /**
     * Удаление записи по id
     */
    @DeleteMapping("/{mpaId}")
    public void deleteMpaSearchId(@PathVariable Integer mpaId) {
        serviceMpa.deleteSearchId(mpaId);
    }
}