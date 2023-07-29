package ru.yandex.practicum.filmorete.enums;

public enum EnumRatingFilm {

    RATING_FILM__G("G", "У фильма нет возрастных ограничений."),
    RATING_FILM__PG("PG", "Детям рекомендуется смотреть фильм с родителями."),
    RATING_FILM__PG_13("PG-13", "Детям до 13 лет просмотр не желателен."),
    RATING_FILM__R("R", "Лицам до 17 лет просматривать фильм можно только в присутствии взрослого."),
    RATING_FILM__NC_17("NC-17", "Лицам до 18 лет просмотр запрещён.");

    final String name;
    final String description;

    EnumRatingFilm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
