package ru.yandex.practicum.filmorete.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundGenreStorage;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.dao.RosterGenreDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceGenre.SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS;

@Slf4j
@Service
public class ServiceGenre {

    private final RosterGenreDao genreDao;

    private ServiceGenre(RosterGenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre getGenresSearchId(Integer genreId) {

        Optional<Genre> optional = genreDao.findRow(genreId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundGenreStorage(SERVICE_ERROR_GENRE_NOT_IN_COLLECTIONS);
        }
    }

    public List<Genre> getAllGenres() {
        return genreDao.findRows();
    }

    public void add(@NotNull Genre genre) {
        if (genre.getId() == null) {
            genreDao.insert(
                    genre.getName()
            );
        } else {
            genreDao.insert(
                    genre.getId(), genre.getName()
            );
        }
    }

    public void update(@NotNull Genre genre) {
        if (genre.getId() != null) {
            genreDao.update(
                    genre.getId(),
                    genre.getName()
            );
        }
    }

    public void deleteAll() {
        genreDao.delete();
    }

    public void deleteSearchId(Integer genreId) {
        genreDao.delete(genreId);
    }
}
