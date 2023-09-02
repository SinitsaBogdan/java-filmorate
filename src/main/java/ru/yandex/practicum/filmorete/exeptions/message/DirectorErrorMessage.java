package ru.yandex.practicum.filmorete.exeptions.message;

public enum DirectorErrorMessage {

    SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS(
            "Режиссёр не найден.",
            "Режиссёр отсутствует в программе!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    DirectorErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
