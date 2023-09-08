package ru.yandex.practicum.filmorete.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.exeptions.FilmorateException;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.dao.RosterMpaDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorete.exeptions.ResponseErrorMessage.ERROR__MPA__NOT_IN_MPA_COLLECTIONS;

@Service
public class ServiceMpa {

    private final RosterMpaDao mpaDao;

    @Autowired
    private ServiceMpa(RosterMpaDao ratingDao) {
        this.mpaDao = ratingDao;
    }

    public Mpa getSearchId(Integer mpaId) {
        Optional<Mpa> optional = mpaDao.findMpaById(mpaId);
        if (optional.isEmpty()) throw new FilmorateException(ERROR__MPA__NOT_IN_MPA_COLLECTIONS);
        return optional.get();
    }

    public List<Mpa> getAll() {
        return mpaDao.findAllMpa();
    }

    public void add(@NotNull Mpa mpa) {
        Optional<Mpa> optionalMpa = mpaDao.findMpaById(mpa.getId());
        if (optionalMpa.isPresent()) throw new FilmorateException(ERROR__MPA__NOT_IN_MPA_COLLECTIONS);
        mpaDao.insert(mpa.getName(), mpa.getDescription());
    }

    public void update(@NotNull Mpa mpa) {
        Optional<Mpa> optionalMpa = mpaDao.findMpaById(mpa.getId());
        if (optionalMpa.isEmpty()) throw new FilmorateException(ERROR__MPA__NOT_IN_MPA_COLLECTIONS);
        mpaDao.update(mpa.getId(), mpa.getName(), mpa.getDescription());
    }

    public void deleteAll() {
        mpaDao.delete();
    }

    public void deleteSearchId(Integer mpaId) {
        Optional<Mpa> optionalMpa = mpaDao.findMpaById(mpaId);
        if (optionalMpa.isEmpty()) throw new FilmorateException(ERROR__MPA__NOT_IN_MPA_COLLECTIONS);
        mpaDao.deleteById(mpaId);
    }
}
