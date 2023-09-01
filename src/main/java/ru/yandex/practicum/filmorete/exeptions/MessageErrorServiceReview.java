package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorServiceReview {

    SERVICE_ERROR_REVIEW_NOT_IN_COLLECTIONS(
            "Отзыв не найден.",
            "Отзыв отсутствует в программе!",
            404
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    MessageErrorServiceReview(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
