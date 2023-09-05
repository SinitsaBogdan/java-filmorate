package ru.yandex.practicum.filmorete.exeptions;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class ResponseError {

    @NonNull
    private final String name;

    @NonNull
    private final String description;
}
