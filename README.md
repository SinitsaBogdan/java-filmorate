# FILMORATE

---
## Задачи на спринт
1. Проверить и внедрить Валидатор
2. Проверить и внедрить исключения
3. Добавление новых контроллеров
4. Оптимизировать Dao классы (повторяющийся код)
5. Тестирование


---
## Список и описание таблиц БД

ER - СХЕМА
![ER.jpg](ER.jpg)

1. USER
2. FILM
3. TOTAL_USER_FRIENDS
4. TOTAL_FILM_LIKE
5. TOTAL_GENRE_FILM
6. ENUM_GENRE
7. ENUM_RATING
8. ENUM_STATUS_FRIENDS

---
## Методы приложения, моделей и сервисов

### USER
1. Запрос всего списка пользователей
2. Запрос конкретного пользователя
3. Обновление пользователя
4. Удаление пользователя

### FILM
1. Запрос всего списка фильмов
2. Запрос конкретного фильма
3. Обновление фильма
4. Удаление фильма

### SERVICE USER
1. Добавление пользователя в друзья
2. Подтверждение заявки в друзья
3. Удаление пользователя из друзей
4. Запрос списка друзей
5. Запрос пользователя из списка друзей

### SERVICE FILM
1. Поставить лайк фильму
2. Убрать лайк у фильма
3. Запрос топ фильмов по лайкам

### SERVICE FILMORATE
1.
