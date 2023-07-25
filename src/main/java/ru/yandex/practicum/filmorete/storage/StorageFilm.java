package ru.yandex.practicum.filmorete.storage;

import ru.yandex.practicum.filmorete.model.Film;

import java.util.Collection;
import java.util.Set;

public interface StorageFilm {

    Long getLastIdentification();

    Set<Long> getCollectionsIdFilms();

    Collection<Film> getFilm();

    Film getFilm(Long id);

    void addFilm(Film film);

    void updateFilm(Film film);

    Film removeFilm(Long id);

    Set<String> getNames();

    void addName(String name);

    Boolean removeName(String name);

    void clear();
}
