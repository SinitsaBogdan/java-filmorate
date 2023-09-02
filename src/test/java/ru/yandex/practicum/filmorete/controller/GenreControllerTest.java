package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.model.Genre;
import ru.yandex.practicum.filmorete.sql.impl.RosterGenreDaoImpl;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RosterGenreDaoImpl enumGenreDao;

    List<Genre> listGenre = Arrays.asList(
            Genre.builder().id(1).name("T").build(),
            Genre.builder().id(2).name("E").build(),
            Genre.builder().id(3).name("S").build()
    );


    @BeforeEach
    public void beforeEach() {
        enumGenreDao.deleteAll();
        enumGenreDao.insert(
                listGenre.get(0).getId(),
                listGenre.get(0).getName()
        );
        enumGenreDao.insert(
                listGenre.get(1).getId(),
                listGenre.get(1).getName()
        );
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос списка записей в системе")
        public void testGetAll() throws Exception {
            mockMvc.perform(get("/genres"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$.[0].id").value(1))
                    .andExpect(jsonPath("$.[0].name").value("T"))
            ;
        }

        @Test
        @DisplayName("Запрос существующей записи")
        public void testGetSearchId() throws Exception {
            mockMvc.perform(get("/genres/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("T"))
            ;
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Добавление новой записи")
        public void testGetSearchId() throws Exception {
            mockMvc.perform(post("/genres")
                            .content(objectMapper.writeValueAsString(listGenre.get(2)))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/genres"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$.[2].id").value(3))
                    .andExpect(jsonPath("$.[2].name").value("S"))
            ;
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление записи по id")
        public void testDeleteAll() throws Exception {
            mockMvc.perform(put("/genres")
                            .content(objectMapper.writeValueAsString(
                                    Genre.builder().id(1).name("W").build()
                            ))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/genres/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("W"))
            ;
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление всего списка")
        public void testDeleteAll() throws Exception {
            mockMvc.perform(delete("/genres"))
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/genres"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0))
            ;
        }

        @Test
        @DisplayName("Удаление по id")
        public void testDeleteSearchId() throws Exception {
            mockMvc.perform(delete("/genres/1"))
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/genres"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
            ;
        }
    }
}
