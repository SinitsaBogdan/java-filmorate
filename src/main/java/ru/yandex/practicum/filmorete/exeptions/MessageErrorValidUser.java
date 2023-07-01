package ru.yandex.practicum.filmorete.exeptions;

public enum MessageErrorValidUser {

    VALID_ERROR_USER_DOUBLE_IN_COLLECTIONS("Пользователь с такой электронной почтой уже зарегестрирован!"),
    VALID_ERROR_USER_NOT_ID("Не указан ID пользователя!"),
    VALID_ERROR_USER_ID_NOT_CORRECT("Указан не корректный ID"),
    VALID_ERROR_USER_ID_NOT_IN_COLLECTIONS("Пользователь с таким ID не найден!"),
    VALID_ERROR_USER_NOT_BIRTHDAY("Не указана дата рождения!"),
    VALID_ERROR_USER_BIRTHDAY_MAX("Дата рождения не должна быть в будующем!"),
    VALID_ERROR_USER_NOT_LOGIN("Не указан логин!"),
    VALID_ERROR_USER_LOGIN_IS_WHITESPACE("Логин не должен содержать пробелы!"),
    VALID_ERROR_USER_NOT_EMAIL("Не указана электронная почта!"),
    VALID_ERROR_USER_EMAIL_NOT_CORRECT("Указана не корректная электронная почта!");

    private final String message;

    MessageErrorValidUser(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
