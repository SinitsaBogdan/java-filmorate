package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class StatusFriends {

    @Positive
    private Long id;

    @Positive
    private String status;
}
