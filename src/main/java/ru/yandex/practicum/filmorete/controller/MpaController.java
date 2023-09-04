package ru.yandex.practicum.filmorete.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.service.ServiceMpa;

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
        log.info("GET-запрос: получение списка всех mpa.");
        return serviceMpa.getAll();
    }

    /**
     * Добавление новой записи
     */
    @PostMapping
    public void post(@Valid @RequestBody Mpa mpa) {
        log.info("Post-запрос: добавление нового mpa: {}.", mpa);
        serviceMpa.add(mpa);
    }

    /**
     * Обновление существующей записи
     */
    @PutMapping
    public void put(@Valid @RequestBody Mpa mpa) {
        log.info("Put-запрос: обновление существующего mpa: {}.", mpa);
        serviceMpa.update(mpa);
    }

    /**
     * Удаление всех записей
     */
    @DeleteMapping
    public void deleteAll() {
        log.info("Delete-запрос: удаление всех mpa.");
        serviceMpa.deleteAll();
    }

    /**
     * Запрос записи по id
     */
    @GetMapping("/{mpaId}")
    public Mpa getSearchId(@PathVariable Integer mpaId) {
        log.info("GET-запрос: получение mpa по id {}.", mpaId);
        return serviceMpa.getSearchId(mpaId);
    }

    /**
     * Удаление записи по id
     */
    @DeleteMapping("/{mpaId}")
    public void deleteMpaSearchId(@PathVariable Integer mpaId) {
        log.info("Delete-запрос: удаление mpa по id {}.", mpaId);
        serviceMpa.deleteSearchId(mpaId);
    }
}