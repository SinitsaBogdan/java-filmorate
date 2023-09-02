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

    public static @NotNull Director buildDirector (@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        String name = row.getString("NAME");
        return Director.builder().id(id).name(name).build();
    }

    public static @NotNull Event buildEvent(@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        System.out.println(row.getString("TIMESTAMP"));
        Long timestamp = Objects.requireNonNull(row.getTimestamp("TIMESTAMP")).getTime();
        Long userId = row.getLong("USER_ID");
        EventType type = EventType.valueOf(row.getString("TYPE"));
        EventOperation operation = EventOperation.valueOf(row.getString("OPERATION"));
        Long entityId = row.getLong("ENTITY_ID");
        return Event.builder()
                .eventId(id)
                .timestamp(timestamp)
                .userId(userId)
                .eventType(type)
                .operation(operation)
                .entityId(entityId)
                .build();
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
        return Film.builder()
                .id(filmId)
                .name(filmName)
                .description(filmDescription)
                .releaseDate(releaseDate)
                .duration(duration)
                .rate(rate)
                .mpa(mpa)
                .build();
    }

    public static @NotNull Genre buildGenre(@NotNull SqlRowSet row) {
        Integer id = row.getInt("ID");
        String name = row.getString("NAME");
        return Genre.builder().id(id).name(name).build();
    }

    public static @NotNull Mpa buildMpa(@NotNull SqlRowSet row) {
        Integer id = row.getInt("ID");
        String name = row.getString("NAME");
        String description = row.getString("DESCRIPTION");
        return Mpa.builder().id(id).name(name).description(description).build();
    }

    public static @NotNull Review buildReview(@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        String content = row.getString("CONTENT");
        Boolean isPositive = row.getBoolean("IS_POSITIVE");
        Long userId = row.getLong("USER_ID");
        Long filmId = row.getLong("FILM_ID");
        Integer useful = row.getInt("USEFUL");
        return Review.builder()
                .reviewId(id)
                .content(content)
                .isPositive(isPositive)
                .userId(userId)
                .filmId(filmId)
                .useful(useful)
                .build();
    }

    public static @NotNull User buildUser(@NotNull SqlRowSet row) {
        Long id = row.getLong("ID");
        String name = row.getString("NAME");
        LocalDate birthday = Objects.requireNonNull(row.getDate("BIRTHDAY")).toLocalDate();
        String login = row.getString("LOGIN");
        String email = row.getString("EMAIL");
        return User.builder()
                .id(id)
                .name(name)
                .birthday(birthday)
                .login(login)
                .email(email)
                .build();
    }

    public static TotalDirectorFilm buildTotalDirectorFilm(SqlRowSet row) {
        Long filmId = row.getLong("FILM_ID");
        Long directorId = row.getLong("DIRECTOR_ID");
        return TotalDirectorFilm.builder().filmId(filmId).directorId(directorId).build();
    }

    public static @NotNull TotalGenreFilm buildTotalGenreFilm(@NotNull SqlRowSet row) {
        Long filmId = row.getLong("FILM_ID");
        Long genreId = row.getLong("GENRE_ID");
        return TotalGenreFilm.builder().filmId(filmId).genreId(genreId).build();
    }

    public static @NotNull TotalLikeFilm buildTotalLikeFilm(@NotNull SqlRowSet row) {
        Long filmId = row.getLong("FILM_ID");
        Long userId = row.getLong("USER_ID");
        return TotalLikeFilm.builder().filmId(filmId).userId(userId).build();
    }

    public static @NotNull TotalLikeReview buildTotalLikeReview(@NotNull SqlRowSet row) {
        Long reviewId = row.getLong("REVIEW_ID");
        Long userId = row.getLong("USER_ID");
        boolean typeLike = row.getBoolean("IS_POSITIVE");
        return TotalLikeReview.builder().reviewId(reviewId).userId(userId).typeLike(typeLike).build();
    }

    public static @NotNull TotalUserFriends buildTotalUserFriends(@NotNull SqlRowSet row) {
        Long userId = row.getLong("USER_ID");
        Long friendId = row.getLong("FRIEND_ID");
        StatusFriend statusFriend = StatusFriend.valueOf(row.getString("STATUS"));
        return TotalUserFriends.builder().userId(userId).friendId(friendId).statusFriend(statusFriend).build();
    }
}