package ru.yandex.practicum.filmorete.exeptions.message;

public enum FilmErrorMessage {

    ERROR_COLLECTIONS_IN_NULL(
            "Коллекция фильмов пустая",
            "Не верные параметры запроса или отсутствие результата!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    FilmErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
