package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorValidFilm {

    VALID_ERROR_FILM_DOUBLE_IN_COLLECTIONS(
        "Ошибка обработки поля name",
        "Фильм уже есть в коллекции!",
        400
    ),
    VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS(
        "Ошибка обработки поля id",
        "Указанный ID фильма отсутствует в коллекции!",
        404
    ),
    VALID_ERROR_FILM_NOT_ID(
        "Ошибка формирования запроса",
        "Не указан ID фильма!",
        400
    ),
    VALID_ERROR_FILM_NOT_NAME(
        "Ошибка формирования запроса",
        "Не указанно имя фильма!",
        400
    ),
    VALID_ERROR_FILM_NOT_DESCRIPTION(
        "Ошибка формирования запроса",
        "Не указанно описание фильма!",
        400
    ),
    VALID_ERROR_FILM_NOT_RELEASED(
        "Ошибка формирования запроса",
        "Не указана дата релиза фильма!",
        400
    ),
    VALID_ERROR_FILM_NOT_DURATION(
        "Ошибка формирования запроса",
        "Не указана продолжительность фильма!",
        400
    ),
    VALID_ERROR_FILM_DESCRIPTION_MAX_LENGTH(
        "Ошибка валидации поля description",
        "Описание фильма не должно превышать 200 символов!",
        400
    ),
    VALID_ERROR_FILM_ID_MIN(
        "Ошибка валидации поля id",
        "ID фильма должен быть положительным числом!",
        400
    ),
    VALID_ERROR_FILM_DURATION_MIN(
        "Ошибка валидации поля duration",
        "Продолжительность фильма должна быть положительным числом!",
        400
    ),
    VALID_ERROR_FILM_RELEASED_MIN(
        "Ошибка валидации поля released",
        "Дата релиза фильма не должна быть ранее 28 декабря 1895 года!",
        400
    );

    private final String name;
    private final String description;
    private final int httpStatusCode;

    MessageErrorValidFilm(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
