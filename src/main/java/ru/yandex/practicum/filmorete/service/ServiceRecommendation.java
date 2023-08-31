package ru.yandex.practicum.filmorete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorete.model.Film;
import ru.yandex.practicum.filmorete.sql.dao.TotalFilmLikeDao;

import java.util.List;

@Service
public class ServiceRecommendation {

    TotalFilmLikeDao totalFilmLikeDao;

    @Autowired
    public ServiceRecommendation(TotalFilmLikeDao totalFilmLikeDao) {
        this.totalFilmLikeDao = totalFilmLikeDao;
    }

    public List<Film> getRecommendation(Long userId) {
        return totalFilmLikeDao.getRecommendationForUser(userId);
    }

}
