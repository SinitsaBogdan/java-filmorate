-- TABLE ROSTER_RATING

INSERT INTO ROSTER_MPA (NAME, DESCRIPTION)
VALUES
        ('G', 'У фильма нет возрастных ограничений'),
        ('PG', 'Детям рекомендуется смотреть фильм с родителями'),
        ('PG-13', 'Детям до 13 лет просмотр не желателен'),
        ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
        ('NC-17', 'Лицам до 18 лет просмотр запрещён')
;

-- TABLE ROSTER_GENRE

INSERT INTO ROSTER_GENRE (NAME)
VALUES  ('Комедия'),
        ('Драма'),
        ('Мультфильм'),
        ('Триллер'),
        ('Документальный'),
        ('Боевик')
;

-- TABLE ROSTER_STATUS_FRIENDS

INSERT INTO ROSTER_STATUS_FRIENDS (NAME)
VALUES  ('Не подтвержденная'),
        ('Подвержденная')
;