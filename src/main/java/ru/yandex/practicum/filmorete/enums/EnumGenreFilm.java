package ru.yandex.practicum.filmorete.enums;

public enum EnumGenreFilm {

    GENRE_FILM__COMEDY("Комедия"),
    GENRE_FILM__DRAMA("Драма"),
    GENRE_FILM__CARTOON("Мультфильм"),
    GENRE_FILM__THRILLER("Триллер"),
    GENRE_FILM__DOCUMENTARY("Документальный"),
    GENRE_FILM__ACTION_MOVIE("Боевик");

    final String name;

    EnumGenreFilm(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
