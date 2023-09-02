package ru.yandex.practicum.filmorete.exeptions.message;

public enum GenreErrorMessage {

    SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS(
            "Жанр не найден.",
            "Жанр отсутствует в списке!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    GenreErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
