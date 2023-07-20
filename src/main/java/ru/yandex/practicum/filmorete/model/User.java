package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@Jacksonized
public class User {

    @Positive
    private Long id;

    private String name;

    @NonNull
    @PastOrPresent
    private LocalDate birthday;

    @NotBlank
    private String login;

    @Email
    @NotBlank
    private String email;

    @Builder.Default
    private Set<Long> likesFilms;

    @Builder.Default
    private Set<Long> friends;

    @Builder.Default
    private long sizeFriends = 0L;

    @Builder.Default
    private long sizeLikes = 0L;

    public void addFriend(User user) {
        friends.add(user.getId());
        sizeFriends++;
    }

    public void removeFriend(User user) {
        friends.remove(user.getId());
        sizeFriends--;
    }

    public void clearFriend() {
        friends.clear();
    }

    public void addLike(Film film) {
        likesFilms.add(film.getId());
        sizeLikes++;
    }

    public void removeLikes(Film film) {
        likesFilms.remove(film.getId());
        sizeLikes--;
    }

    public void clearLikes() {
        likesFilms.clear();
    }
}