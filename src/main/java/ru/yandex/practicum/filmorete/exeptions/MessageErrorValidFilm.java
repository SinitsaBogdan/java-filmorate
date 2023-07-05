package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorValidFilm {

    VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS("Фильм уже есть в коллекции!"),
    VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS("Указанный ID фильма отсутствует в коллекции!"),
    VALID_ERROR_FILM_NOT_ID("Не указан ID фильма!"),
    VALID_ERROR_FILM_NOT_NAME("Не указанно имя фильма!"),
    VALID_ERROR_FILM_NOT_DESCRIPTION("Не указанно описание фильма!"),
    VALID_ERROR_FILM_NOT_RELEASED("Не указана дата релиза фильма!"),
    VALID_ERROR_FILM_NOT_DURATION("Не указана продолжительность фильма!"),
    VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH("Описание фильма не должно превышать 200 символов!"),
    VALID_ERROR_FILM_ID_MIN("ID фильма должен быть положительным числом!"),
    VALID_ERROR_FILM_DURATION_MIN("Продолжительность фильма должна быть положительным числом!"),
    VALID_ERROR_FILM_RELEASED_MIN("Дата релиза фильма не должна быть ранее 28 декабря 1895 года!");

    private final String message;

    MessageErrorValidFilm(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
