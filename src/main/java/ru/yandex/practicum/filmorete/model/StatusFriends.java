package ru.yandex.practicum.filmorete.model;

import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusFriends {

    @Positive
    private Long id;

    @Positive
    private String status;
}
