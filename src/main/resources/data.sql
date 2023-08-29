-- TABLE ROSTER_RATING

INSERT INTO ROSTER_MPA (name, description)
VALUES
        ('G', 'У фильма нет возрастных ограничений'),
        ('PG', 'Детям рекомендуется смотреть фильм с родителями'),
        ('PG-13', 'Детям до 13 лет просмотр не желателен'),
        ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
        ('NC-17', 'Лицам до 18 лет просмотр запрещён')
;

-- TABLE ROSTER_GENRE

INSERT INTO ROSTER_GENRE (name)
VALUES  ('Комедия'),
        ('Драма'),
        ('Мультфильм'),
        ('Триллер'),
        ('Документальный'),
        ('Боевик')
;

-- TABLE ROSTER_STATUS_FRIENDS

INSERT INTO ROSTER_STATUS_FRIENDS (name)
VALUES  ('Не подтвержденная'),
        ('Подтвержденная')
;

--TABLE ROSTER_TYPE_REVIEW

INSERT INTO ROSTER_TYPE_REVIEW (name)
VALUES ('Положительный'),
       ('Нейтральный'),
       ('Отрицательный')
;

--TABLE ROSTER_EVALUATION

INSERT INTO ROSTER_EVALUATION (name)
VALUES ('Полезный'),
       ('Нейтральный'),
       ('Безполезный')
;

--TABLE ROSTER_EVENT_TYPE

INSERT INTO ROSTER_EVENT_TYPE (name)
VALUES ('CREATE FILM'),
       ('CREATE REVIEW'),
       ('PUT LIKE'),
       ('PUT FRIEND').
       ('UPDATE FILM'),
       ('UPDATE REVIEW'),
       ('UPDATE FRIEND'),
       ('DELETE FILM'),
       ('DELETE FRIEND'),
       ('DELETE LIKE'),
       ('DELETE REVIEW')
;

