package ru.yandex.practicum.filmorete.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorete.enums.EventOperation;
import ru.yandex.practicum.filmorete.enums.EventType;
import ru.yandex.practicum.filmorete.enums.StatusFriend;
import ru.yandex.practicum.filmorete.model.*;

import java.time.LocalDate;
import java.util.Objects;

public class FactoryModel {

    private FactoryModel() {}

    public static @NotNull Director buildDirector (@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        String name = row.getString("NAME");
        return new Director(id, name);
    }

    public static @NotNull Event buildEvent(@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        Long timestamp = row.getLong("TIMESTAMP");
        Long userId = row.getLong("USER_ID");
        EventType type = EventType.valueOf(row.getString("TYPE"));
        EventOperation operation = EventOperation.valueOf(row.getString("OPERATION"));
        Long entityId = row.getLong("ENTITY_ID");
        return new Event(id, timestamp, userId, type, operation, entityId);
    }

    public static @NotNull Film buildFilm(@NotNull SqlRowSet row) {
        Integer mpaId = row.getInt("MPA_ID");
        String mpaName = row.getString("MPA_NAME");
        Mpa mpa = Mpa.builder().id(mpaId).name(mpaName).build();

        Long filmId = row.getLong("FILM_ID");
        String filmName = row.getString("FILM_NAME");
        String filmDescription = row.getString("FILM_DESCRIPTION");
        LocalDate releaseDate = Objects.requireNonNull(row.getDate("FILM_RELEASE_DATE")).toLocalDate();
        Integer duration = row.getInt("FILM_DURATION");
        Integer rate = row.getInt("FILM_RATE");
        return new Film(filmId, filmName, filmDescription, releaseDate, duration, rate, mpa);
    }

    public static @NotNull Genre buildGenre(@NotNull SqlRowSet row) {
        Integer id = row.getInt("ID");
        String name = row.getString("NAME");
        return new Genre(id, name);
    }

    public static @NotNull Mpa buildMpa(@NotNull SqlRowSet row) {
        Integer id = row.getInt("ID");
        String name = row.getString("NAME");
        String description = row.getString("DESCRIPTION");
        return new Mpa(id, name, description);
    }

    public static @NotNull Review buildReview(@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        String content = row.getString("CONTENT");
        Boolean isPositive = row.getBoolean("IS_POSITIVE");
        Long userId = row.getLong("USER_ID");
        Long filmId = row.getLong("FILM_ID");
        Integer useful = row.getInt("USEFUL");
        return new Review(id, content, isPositive, userId, filmId, useful);
    }

    public static @NotNull User buildUser(@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        String name = row.getString("NAME");
        LocalDate birthday = Objects.requireNonNull(row.getDate("BIRTHDAY")).toLocalDate();
        String login = row.getString("LOGIN");
        String email = row.getString("EMAIL");
        return new User(id, name, birthday, login, email);
    }

    public static TotalDirectorFilm buildTotalDirectorFilm(SqlRowSet row) {
        return TotalDirectorFilm.builder()
                .build();
    }

    public static @NotNull TotalGenreFilm buildTotalGenreFilm(@NotNull SqlRowSet row) {
        Long filmId = row.getLong("FILM_ID");
        Long directorId = row.getLong("GENRE_ID");
        return new TotalGenreFilm(filmId, directorId);
    }

    public static @NotNull TotalLikeFilm buildTotalLikeFilm(@NotNull SqlRowSet row) {
        Long filmId = row.getLong("FILM_ID");
        Long userId = row.getLong("USER_ID");
        return new TotalLikeFilm(filmId, userId);
    }

    public static @NotNull TotalLikeReview buildTotalLikeReview(@NotNull SqlRowSet row) {
        Long reviewId = row.getLong("REVIEW_ID");
        Long userId = row.getLong("USER_ID");
        Boolean typeLike = row.getBoolean("IS_POSITIVE");
        return new TotalLikeReview(reviewId, userId, typeLike);
    }

    public static @NotNull TotalUserFriends buildTotalUserFriends(@NotNull SqlRowSet row) {
        Long userId = row.getLong("USER_ID");
        Long friendId = row.getLong("FRIEND_ID");
        StatusFriend statusFriend = StatusFriend.valueOf(row.getString("STATUS"));
        return new TotalUserFriends(userId, friendId, statusFriend);
    }
}