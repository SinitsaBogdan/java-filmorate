package ru.yandex.practicum.filmorete.comparators;

import ru.yandex.practicum.filmorete.model.Film;

import java.util.Comparator;

public class ComparatorUserToSeizeLike implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        return Long.compare(o1.getSizeLikes(), o2.getSizeLikes());
    }
}
