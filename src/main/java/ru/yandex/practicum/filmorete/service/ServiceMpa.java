package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.ExceptionNotFoundMpaStorage;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.RosterMpaDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.message.MpaErrorMessage.SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS;

@Service
public class ServiceMpa {

    private final RosterMpaDao mpaDao;

    @Autowired
    private ServiceMpa(RosterMpaDao ratingDao) {
        this.mpaDao = ratingDao;
    }

    public Mpa getSearchId(Integer mpaId) {
        Optional<Mpa> optional = mpaDao.findMpaByRowId(mpaId);
        if (optional.isEmpty()) throw new ExceptionNotFoundMpaStorage(SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS);
        return optional.get();
    }

    public List<Mpa> getAll() {
        return mpaDao.findAllMpa();
    }

    public void add(@NotNull Mpa mpa) {
        Optional<Mpa> optionalMpa = mpaDao.findMpaByRowId(mpa.getId());
        if (optionalMpa.isEmpty()) throw new ExceptionNotFoundMpaStorage(SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS);
        mpaDao.insert(mpa.getName(), mpa.getDescription());
    }

    public void update(@NotNull Mpa mpa) {
        Optional<Mpa> optionalMpa = mpaDao.findMpaByRowId(mpa.getId());
        if (optionalMpa.isEmpty()) throw new ExceptionNotFoundMpaStorage(SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS);
        mpaDao.update(mpa.getId(), mpa.getName(), mpa.getDescription());
    }

    public void deleteAll() {
        mpaDao.delete();
    }

    public void deleteSearchId(Integer mpaId) {
        Optional<Mpa> optionalMpa = mpaDao.findMpaByRowId(mpaId);
        if (optionalMpa.isEmpty()) throw new ExceptionNotFoundMpaStorage(SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS);
        mpaDao.deleteByRowId(mpaId);
    }
}
