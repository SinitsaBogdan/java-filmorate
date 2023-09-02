package ru.yandex.practicum.filmorete.exeptions.message;


public enum MpaErrorMessage {

    SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS(
            "Рейтинг не найден.",
            "Рейтинг отсутствует в списке!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    MpaErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
