package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.comparators.ComparatorUserToSeizeLike;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.User;
import ru.yandex.practicum.filmorete.storage.FilmStorage;
import ru.yandex.practicum.filmorete.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public FilmStorage filmStorage;
    public UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> getFilmsLikesToUser(@NotNull User user) {

        List<Film> result = user.getLikesFilms().stream()
                .map(filmStorage::getFilm)
                .collect(Collectors.toList());

        result.remove(null);
        return result;
    }

    public List<User> getUserLikesToFilm(@NotNull Film film) {
        return film.getLikeUsers().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<Film> getPopularFilms(Integer count) {
        List<Film> list = new ArrayList<>(filmStorage.getFilm());
        list.sort(new ComparatorUserToSeizeLike().reversed());

        if (count > filmStorage.getNames().size()) {
            count = filmStorage.getNames().size();
        }

        try {
            return list.subList(0, count);
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void removeLike(@NotNull Film film, @NotNull User user) {
    }

    public void addLike(Film film, User user) {
        if (user.getLikesFilms() == null) {
            user.setLikesFilms(new HashSet<>());
        }

        if (film.getLikeUsers() == null) {
            film.setLikeUsers(new HashSet<>());
        }

        if (!user.getLikesFilms().contains(film.getId())) {
            user.addLike(film);
        }
        if (!film.getLikeUsers().contains(user.getId())) {
            film.addLike(user);
        }
    }
}
