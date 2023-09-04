package ru.yandex.practicum.filmorete.exeptions.message;

public enum TotalFilmLikeErrorMessage {

    ERROR_DOUBLE_IN_COLLECTIONS(
            "Запись уже есть в программе",
            "Дублирование существующей запси!",
            204
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    TotalFilmLikeErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
