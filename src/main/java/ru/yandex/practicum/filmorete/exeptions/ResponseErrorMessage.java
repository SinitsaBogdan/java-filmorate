package ru.yandex.practicum.filmorete.exeptions;

import lombok.Getter;

@Getter
public enum ResponseErrorMessage {

    GLOBAL_ERROR__COLLECTIONS_IS_EMPTY("Коллекция пустая", "Не верные параметры запроса или отсутствие результата!", 404),

    ERROR__DIRECTOR__NOT_IN_COLLECTIONS("Режиссёр не найден.", "Режиссёр отсутствует в программе!", 404),
    ERROR__DIRECTOR__DUPLICATE_IN_COLLECTIONS("Режиссёр уже есть.", "Режиссёр уже есть в программе!", 203),

    ERROR__USER__ID_NOT_IN_COLLECTIONS("Ошибка обработки поля id", "Пользователь с таким ID не найден!", 404),
    ERROR__USER__DOUBLE_EMAIL_IN_COLLECTIONS("Ошибка обработки поля email", "Пользователь с такой электронной почтой уже зарегестрирован!",400),
    ERROR__USER__NOT_LOGIN("Ошибка формирования запроса", "Не указан логин!", 400),
    ERROR__USER__LOGIN_IS_WHITESPACE("Ошибка валидации поля login", "Логин не должен содержать пробелы!", 400),

    ERROR__FILM__ID_NOT_IN_COLLECTIONS("Ошибка обработки поля id","Указанный ID фильма отсутствует в коллекции!",404),
    ERROR__FILM__DOUBLE_IN_COLLECTIONS("Ошибка добавления фильма", "Фильм уже есть в коллекции!", 203),
    ERROR__FILM__NOT_DURATION("Ошибка формирования запроса", "Не указана продолжительность фильма!", 400),
    ERROR__FILM__DURATION_MIN("Ошибка валидации поля duration","Продолжительность фильма должна быть положительным числом!", 400),
    ERROR__FILM__RELEASED_MIN("Ошибка валидации поля released", "Дата релиза фильма не должна быть ранее 28 декабря 1895 года!", 400),

    ERROR__GENRE__NOT_IN_COLLECTIONS("Жанр не найден.", "Жанр отсутствует в списке!", 404),
    ERROR__MPA__NOT_IN_MPA_COLLECTIONS("Рейтинг не найден.","Рейтинг отсутствует в списке!", 404),
    ERROR__REVIEW__NOT_IN_COLLECTIONS("Отзыв не найден.", "Отзыв отсутствует в программе!", 404),
    ERROR__TOTAL__LIKE_FILM_DOUBLE_IN_COLLECTIONS("Лайк от этого пользователя уже добавлен.", "", 404)
    ;

    private final String name;

    private final String description;

    private final int httpStatusCode;

    ResponseErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
