package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalUserFriends {

    @Positive
    private Long userId;

    @Positive
    private Long friendId;

    @Positive
    private Long statusId;
}
