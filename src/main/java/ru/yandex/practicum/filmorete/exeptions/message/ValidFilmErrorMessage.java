package ru.yandex.practicum.filmorete.exeptions.message;


public enum ValidFilmErrorMessage {

    ERROR_FILM_ID_NOT_IN_COLLECTIONS(
        "Ошибка обработки поля id",
        "Указанный ID фильма отсутствует в коллекции!",
        404
    ),
    ERROR_FILM_DOUBLE_IN_COLLECTIONS(
            "Ошибка добавления фильма",
            "Фильм уже есть в коллекции!",
            203
    ),
    ERROR_FILM_NOT_DURATION(
        "Ошибка формирования запроса",
        "Не указана продолжительность фильма!",
        400
    ),
    ERROR_FILM_DURATION_MIN(
        "Ошибка валидации поля duration",
        "Продолжительность фильма должна быть положительным числом!",
        400
    ),
    ERROR_FILM_RELEASED_MIN(
        "Ошибка валидации поля released",
        "Дата релиза фильма не должна быть ранее 28 декабря 1895 года!",
        400
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    ValidFilmErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
