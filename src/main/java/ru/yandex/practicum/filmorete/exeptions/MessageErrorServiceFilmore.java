package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorServiceFilmore {

    SERVICE_ERROR_POPULAR_FILM_NOT_IN_COLLECTIONS(
        "Список популярных фильмов пуст.",
        "Список популярных фильмов еще не сформирован!",
        404
    ),
    SERVICE_ERROR_USER_NOT_IN_FRIENDS_COLLECTIONS(
            "Пользаватель не найден.",
            "Пользователь не находится в списке друзей!",
            404
    );

    final String name;
    final String description;
    final int httpStatusCode;

    MessageErrorServiceFilmore(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
