package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@Jacksonized
public class User {

    @Positive
    private Long id;

    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    @NotBlank
    private String login;

    @Email
    @NotBlank
    private String email;
}