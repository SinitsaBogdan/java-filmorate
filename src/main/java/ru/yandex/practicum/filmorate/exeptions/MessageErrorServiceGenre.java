package ru.yandex.practicum.filmorate.exeptions;

public enum MessageErrorServiceGenre {

    SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS(
            "Жанр не найден.",
            "Жанр отсутствует в списке!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    MessageErrorServiceGenre(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
