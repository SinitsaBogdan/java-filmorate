package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorete.enums.StatusFriend;

import javax.validation.constraints.Positive;

@Data
@Builder
public class TotalUserFriends {

    @Positive
    private Long userId;

    @Positive
    private Long friendId;

    @Positive
    private StatusFriend statusFriend;
}
