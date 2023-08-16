package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusFriends {

    private Long id;

    private String status;
}
