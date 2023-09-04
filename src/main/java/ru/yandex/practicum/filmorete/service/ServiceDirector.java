package ru.yandex.practicum.filmorete.service;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundDirectorStorage;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;

import static ru.yandex.practicum.filmorete.exeptions.message.DirectorErrorMessage.ERROR_DIRECTOR_DUPLICATE_IN_COLLECTIONS;
import static ru.yandex.practicum.filmorete.exeptions.message.DirectorErrorMessage.ERROR_DIRECTOR_NOT_IN_COLLECTIONS;

@Service
public class ServiceDirector {

    private final DirectorDao directorDao;

    private ServiceDirector(DirectorDao directorsDao) {
        this.directorDao = directorsDao;
    }

    /**
     * Запрос режиссёра из таблицы DIRECTORS по ID [ DIRECTORS ].
     */
    public Director getDirectorSearchId(Long directorId) {
        Optional<Director> result = directorDao.findById(directorId);
        if (result.isEmpty()) throw new ExceptionNotFoundDirectorStorage(ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
        return result.get();
    }

    /**
     * Запрос всех режиссёров из таблицы DIRECTORS [ DIRECTORS ].
     */
    public List<Director> getAllDirector() {
        return directorDao.find();
    }

    /**
     * Добавление нового режиссёра [ DIRECTORS ].
     */
    public Director add(@NotNull Director director) {
        Optional<Director> optionalDirector = directorDao.findByName(director.getName());
        if (optionalDirector.isPresent()) throw new ExceptionNotFoundDirectorStorage(ERROR_DIRECTOR_DUPLICATE_IN_COLLECTIONS);
        Long id = directorDao.insertByName(director.getName());
        return directorDao.findById(id).get();
    }

    /**
     * Обновление существующего режиссёра [ DIRECTORS ].
     */
    public Director update(@NotNull Director director) {
        Optional<Director> optionalDirector = directorDao.findById(director.getId());
        if (optionalDirector.isEmpty()) throw new ExceptionNotFoundDirectorStorage(ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
        directorDao.update(director.getId(), director.getName());
        Optional<Director> result = directorDao.findById(director.getId());
        return result.orElse(null);
    }

    /**
     * Удаление всех режиссёров [ DIRECTORS ].
     */
    public void deleteAll() {
        directorDao.delete();
    }

    /**
     * Удаление режиссёра по ID [ DIRECTORS ].
     */
    public void deleteSearchId(Long directorId) {
        Optional<Director> optional = directorDao.findById(directorId);
        if (optional.isEmpty()) throw new ExceptionNotFoundDirectorStorage(ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
        directorDao.deleteById(directorId);
    }

    /**
     * Удаление режиссёра по имени [ DIRECTORS ].
     */
    public void deleteSearchName(String directorName) {
        Optional<Director> optional = directorDao.findByName(directorName);
        if (optional.isEmpty()) throw new ExceptionNotFoundDirectorStorage(ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
        directorDao.deleteByName(directorName);
    }
}
