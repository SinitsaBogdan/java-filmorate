package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film implements Comparable<Film> {

    @Positive
    private Long id;

    @NotBlank
    private final String name;

    @NotBlank
    @Size(max = 200)
    private final String description;

    @NonNull
    private final LocalDate releaseDate;

    @Positive
    private final Integer duration;

    private Set<Long> likeUsers;

    @Builder.Default
    private long sizeLikes = 0L;

    public void addLike(User user) {
        likeUsers.add(user.getId());
        sizeLikes++;
    }

    public void removeLike(User user) {
        likeUsers.remove(user.getId());
        sizeLikes--;
    }

    @Override
    public int compareTo(Film film) {
        return Math.toIntExact(film.getSizeLikes());
    }
}