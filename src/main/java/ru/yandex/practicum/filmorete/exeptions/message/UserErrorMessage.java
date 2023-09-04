package ru.yandex.practicum.filmorete.exeptions.message;

public enum UserErrorMessage {

    ERROR_USER_DOUBLE_EMAIL_IN_COLLECTIONS(
            "Ошибка обработки поля email",
            "Пользователь с такой электронной почтой уже зарегестрирован!",
            400
    ),
    ERROR_USER_ID_NOT_IN_COLLECTIONS(
        "Ошибка обработки поля id",
        "Пользователь с таким ID не найден!",
        404
    ),
    ERROR_USER_NOT_LOGIN(
        "Ошибка формирования запроса",
        "Не указан логин!",
        400
    ),
    ERROR_USER_LOGIN_IS_WHITESPACE(
        "Ошибка валидации поля login",
        "Логин не должен содержать пробелы!",
        400
    );

    public final String name;

    public final String description;

    public final int httpStatusCode;

    UserErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
