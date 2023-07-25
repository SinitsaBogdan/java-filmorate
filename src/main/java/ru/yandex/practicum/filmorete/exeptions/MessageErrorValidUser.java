package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorValidUser {

    VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS(
        "Ошибка обработки поля email",
        "Пользователь с такой электронной почтой уже зарегестрирован!",
        400
    ),
    VALID_ERROR_USER_NOT_ID(
        "Ошибка формирования запроса",
        "Не указан ID пользователя!",
        400
    ),
    VALID_ERROR_USER_ID_NOT_CORRECT(
        "Ошибка обработки поля id",
        "Указан не корректный ID",
        400
    ),
    VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS(
        "Ошибка обработки поля id",
        "Пользователь с таким ID не найден!",
        404
    ),
    VALID_ERROR_USER_NOT_BIRTHDAY(
        "Ошибка формирования запроса",
        "Не указана дата рождения!",
        400
    ),
    VALID_ERROR_USER_BIRTHDAY_MAX(
        "Ошибка валидации поля birthday",
        "Дата рождения не должна быть в будующем!",
        400
    ),
    VALID_ERROR_USER_NOT_LOGIN(
        "Ошибка формирования запроса",
        "Не указан логин!",
        400
    ),
    VALID_ERROR_USER_LOGIN_IS_WHITESPACE(
        "Ошибка валидации поля login",
        "Логин не должен содержать пробелы!",
        400
    ),
    VALID_ERROR_USER_NOT_EMAIL(
        "Ошибка формирования запроса",
        "Не указана электронная почта!",
        400
    ),
    VALID_ERROR_USER_EMAIL_NOT_CORRECT(
        "Ошибка валидации поля email",
        "Указана не корректная электронная почта!",
        400
    );

    final String name;
    final String description;
    final int httpStatusCode;

    MessageErrorValidUser(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
