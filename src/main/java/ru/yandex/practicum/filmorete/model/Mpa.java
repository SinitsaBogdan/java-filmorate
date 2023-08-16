package ru.yandex.practicum.filmorete.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mpa {

    private Integer id;

    private String name;

    private String description;
}
