package ru.yandex.practicum.filmorete.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static ru.yandex.practicum.filmorete.enums.EnumStatusFriend.STATUS_FRIENDS__NO_RESPONSE;

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

    @JsonIgnore
    private Set<Long> likesFilms;

    @JsonIgnore
    private Map<Long, String> friends;

    @Builder.Default
    private long sizeFriends = 0L;

    @Builder.Default
    private long sizeLikes = 0L;

    public void addFriend(User user) {
        friends.put(user.getId(), STATUS_FRIENDS__NO_RESPONSE.getStatus());
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