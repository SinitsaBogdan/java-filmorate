package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Film {

    @Positive
    private final Long id;

    @NotBlank
    private final String name;

    @NotNull
    @Size(max = 200)
    private final String description;

    @Builder.Default
    private List<Genre> genres = new ArrayList<>();

    @NotNull
    private final LocalDate releaseDate;

    @NotNull
    private final Integer duration;

    private final Integer rate;

    @NotNull
    private Mpa mpa;

    @Builder.Default
    private List<Director> director = new ArrayList<>();

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addDirector(Director director) {
        this.director.add(director);
    }
}