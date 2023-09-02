package ru.yandex.practicum.filmorete.exeptions.message;

public enum FilmErrorMessage {

    SERVICE_ERROR_COLLECTIONS_IN_NULL(
            "Коллекция пустая",
            "Не верные параметры запроса илиотсутствие результата!",
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
