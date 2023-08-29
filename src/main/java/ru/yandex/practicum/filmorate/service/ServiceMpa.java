package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.ExceptionNotFoundMpaStorage;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.sql.dao.RosterMpaDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.exeptions.MessageErrorServiceMpa.SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS;

@Slf4j
@Service
public class ServiceMpa {

    private final RosterMpaDao mpaDao;

    @Autowired
    private ServiceMpa(RosterMpaDao ratingDao) {
        this.mpaDao = ratingDao;
    }

    public Mpa getSearchId(Integer mpaId) {
        Optional<Mpa> optional = mpaDao.findMpa(mpaId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ExceptionNotFoundMpaStorage(SERVICE_ERROR_MPA_NOT_IN_MPA_COLLECTIONS);
        }
    }

    public List<Mpa> getAll() {
        return mpaDao.findAllMpa();
    }

    public void add(@NotNull Mpa mpa) {
        if (mpa.getId() == null) {
            mpaDao.insert(
                    mpa.getName(), mpa.getDescription()
            );
        } else {
            mpaDao.insert(
                    mpa.getId(), mpa.getName(), mpa.getDescription()
            );
        }
    }

    public void update(@NotNull Mpa mpa) {
        if (mpa.getId() != null) {
            mpaDao.update(
                    mpa.getId(),
                    mpa.getName(),
                    mpa.getDescription()
            );
        }
    }

    public void deleteAll() {
        mpaDao.delete();
    }

    public void deleteSearchId(Integer mpaId) {
        mpaDao.delete(mpaId);
    }
}
