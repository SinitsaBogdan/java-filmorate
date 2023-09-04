package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundFilmStorage;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundGenreStorage;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.message.GenreErrorMessage.SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.message.ValidFilmErrorMessage.VALID_ERROR_FILM_ID_NOT_IN_COLLECTIONS;

@Service
public class ServiceGenre {

    private final RosterGenreDao genreDao;

    private ServiceGenre(RosterGenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre getGenresSearchId(Integer genreId) {
        Optional<Genre> optional = genreDao.findAllByRowId(genreId);
        if (optional.isEmpty()) throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS);
        return optional.get();
    }

    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    public void add(@NotNull Genre genre) {
        Optional<Genre> optional = genreDao.findAllByName(genre.getName());
        if (optional.isPresent()) throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS);
        if (genre.getId() == null) genreDao.insert(genre.getName());
        else genreDao.insert(genre.getId(), genre.getName());
    }

    public void update(@NotNull Genre genre) {
        Optional<Genre> optional = genreDao.findAllByName(genre.getName());
        if (optional.isEmpty()) throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS);
        genreDao.update(genre.getId(), genre.getName());
    }

    public void deleteAll() {
        genreDao.deleteAll();
    }

    public void deleteSearchId(Integer genreId) {
        Optional<Genre> optional = genreDao.findAllByRowId(genreId);
        if (optional.isEmpty()) throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS);
        genreDao.deleteAllByRowId(genreId);
    }
}
