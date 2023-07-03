package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@Builder
public class User {

    @Positive
    private Integer id;

    private String name;

    @NonNull
    @PastOrPresent
    private LocalDate birthday;

    @NotBlank
    @NonNull
    private String login;

    @Email
    @NotBlank
    @NonNull
    private String email;
}