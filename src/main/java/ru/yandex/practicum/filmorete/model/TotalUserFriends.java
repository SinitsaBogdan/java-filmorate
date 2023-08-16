package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalUserFriends {

    private Long userId;

    private Long friendId;

    private Long statusId;
}
