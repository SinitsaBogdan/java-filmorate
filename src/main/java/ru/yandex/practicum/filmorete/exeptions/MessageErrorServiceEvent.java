package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorServiceEvent {

    SERVICE_ERROR_EVENT_COLLECTIONS_IS_NULL(
            "Пустая коллекция событий",
            "Нет записей о событиях!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    MessageErrorServiceEvent(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
