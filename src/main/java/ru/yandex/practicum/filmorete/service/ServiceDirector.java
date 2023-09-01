package ru.yandex.practicum.filmorete.service;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundDirectorStorage;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;

import static ru.yandex.practicum.filmorete.exeptions.MessageErrorServiceDirector.SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS;

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
        if (result.isPresent()) return result.get();
        else throw new ExceptionNotFoundDirectorStorage(SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
    }

    /**
     * Запрос всех режиссёров из таблицы DIRECTORS [ DIRECTORS ].
     */
    public List<Director> getAllDirector() {
        return directorDao.findAll();
    }

    /**
     * Добавление нового режиссёра [ DIRECTORS ].
     */
    public Director add(@NotNull Director director) {
        Long id = directorDao.insert(director.getName());
        Optional<Director> result = directorDao.findById(id);
        return result.orElse(null);
    }

    /**
     * Обновление существующего режиссёра [ DIRECTORS ].
     */
    public Director update(@NotNull Director director) {
        Optional<Director> optionalDirector = directorDao.findById(director.getId());
        if (optionalDirector.isPresent()) {
            directorDao.update(director.getId(), director.getName());
            Optional<Director> result = directorDao.findById(director.getId());
            return result.orElse(null);
        } else throw new ExceptionNotFoundDirectorStorage(SERVICE_ERROR_DIRECTOR_NOT_IN_COLLECTIONS);
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
        directorDao.delete(directorId);
    }

    /**
     * Удаление режиссёра по имени [ DIRECTORS ].
     */
    public void deleteSearchName(String directorName) {
        directorDao.delete(directorName);
    }
}
