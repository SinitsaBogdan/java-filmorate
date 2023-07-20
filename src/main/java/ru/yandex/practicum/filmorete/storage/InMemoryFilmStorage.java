package ru.yandex.practicum.filmorete.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorete.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final Set<String> names = new HashSet<>();
    private Long lastIdentification = 1L;

    @Override
    public Long getLastIdentification() {
        return lastIdentification++;
    }

    @Override
    public Set<Long> getCollectionsIdFilms() {
        return films.keySet();
    }

    @Override
    public Collection<Film> getFilm() {
        return films.values();
    }

    @Override
    public Film getFilm(Long id) {
        return films.get(id);
    }

    @Override
    public void addFilm(Film film) {
        film.setId(getLastIdentification());
        films.put(film.getId(), film);
        names.add(film.getName());
    }

    @Override
    public void updateFilm(Film film) {
        Film oldFilm = films.get(film.getId());
        names.remove(oldFilm.getName());
        names.add(film.getName());
        films.put(oldFilm.getId(), film);
    }

    @Override
    public Film removeFilm(Film film) {
        return films.remove(film.getId());
    }

    @Override
    public Set<String> getNames() {
        return names;
    }

    @Override
    public void addName(String name) {
        names.add(name);
    }

    @Override
    public Boolean removeName(String name) {
        return names.remove(name);
    }

    @Override
    public void clear() {
        films.clear();
        names.clear();
        lastIdentification = 1L;
    }
}
