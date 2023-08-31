package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.model.Director;
import ru.yandex.practicum.filmorete.sql.dao.DirectorDao;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class DirectorControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectorDao directorDao;

    private final Director duplicate = Director.builder().name("director-1").id(1L).build();

    List<Director> listDirector = Arrays.asList(
            Director.builder().name("director-1").build(),
            Director.builder().name("director-2").build()
    );

    @BeforeEach
    public void beforeEach() {
        directorDao.delete();
        directorDao.insert(1L, listDirector.get(0).getName());
        directorDao.insert(2L, listDirector.get(1).getName());
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Получение режиссёра: ID 1")
        void methodGet_DirectorId1Test() throws Exception {
            mockMvc.perform(get("/directors/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("director-1"))
            ;
        }

        @Test
        @DisplayName("Запрос режиссёра: ID 9999")
        public void methodGet_DirectorId9999Test() throws Exception {
            mockMvc.perform(get("/directors/9999"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Запрос режиссёра: ID -1")
        public void methodGet_DirectorIdMinus1Test() throws Exception {
            mockMvc.perform(get("/directors/-1"))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Получение всех режиссёров")
        void methodGet_AllDirectors() throws Exception {
            mockMvc.perform(get("/directors"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$.[0].id").value(1))
                    .andExpect(jsonPath("$.[1].name").value("director-2"))
            ;
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Проверка добавления дубликата режиссёра")
        void methodPost_NewDirectorValidTrue_AndDoubleFalseTest() throws Exception {
            mockMvc.perform(post("/directors").content(objectMapper.writeValueAsString(duplicate)))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : null")
        public void methodPost_NewDirectorValidTrue_NameNullTest() throws Exception {
            Director director = Director.builder()
                    .id(99L)
                    .name(null)
                    .build();
            mockMvc.perform(post("/directors").content(objectMapper.writeValueAsString(director)))
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление режиссёра")
        void methodPut_DirectorValidTrueTest() throws Exception {
            Director director = Director.builder()
                    .id(1L)
                    .name("UPDATE director-1")
                    .build();
            mockMvc.perform(put("/directors").content(objectMapper.writeValueAsString(director))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(1L))
                    .andExpect(jsonPath("name").value("UPDATE director-1"));
        }

        @Test
        @DisplayName("Обновление режиссёра - id : null")
        public void methodPut_DirectorValidFalse_IdNullTest() throws Exception {
            Director director = Director.builder()
                    .id(null)
                    .name("update")
                    .build();
            mockMvc.perform(put("/directors").content(objectMapper.writeValueAsString(director))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление режиссёра - id : 99")
        public void methodPut_DirectorValidFalse_IdNotInCollectionsTest() throws Exception {
            Director director = Director.builder()
                    .id(99L)
                    .name("update")
                    .build();
            mockMvc.perform(put("/directors").content(objectMapper.writeValueAsString(director))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление режиссёра - name : empty")
        public void methodPut_DirectorValidFalse_NameEmptyTest() throws Exception {
            Director director = Director.builder()
                    .id(1L)
                    .name("")
                    .build();
            mockMvc.perform(put("/directors").content(objectMapper.writeValueAsString(director))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
            ;
        }

        @Test
        @DisplayName("Обновление режиссёра - name : null")
        public void methodPut_DirectorValidFalse_NameNullTest() throws Exception {
            Director director = Director.builder()
                    .id(1L)
                    .name(null)
                    .build();
            mockMvc.perform(put("/directors").content(objectMapper.writeValueAsString(director))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
            ;
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление всех режиссёров")
        void methodDelete_DeleteAllDirectorTest() throws Exception {
            mockMvc.perform(delete("/directors"))
                    .andExpect(status().isOk());
            mockMvc.perform(get("/directors"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0))
            ;
        }

        @Test
        @DisplayName("Удаление режиссёра по ID")
        void methodDelete_DeleteDirectorByIdTest() throws Exception {
            mockMvc.perform(delete("/directors/1"))
                    .andExpect(status().isOk());
            mockMvc.perform(get("/directors"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$.[0].id").value(2))
                    .andExpect(jsonPath("$.[0].name").value("director-2"))
            ;
        }

        @Test
        @DisplayName("Удаление режиссёра по NAME")
        void methodDelete_DeleteDirectorByNameTest() throws Exception {
            mockMvc.perform(delete("/directors/name/director-1"))
                    .andExpect(status().isOk());
            mockMvc.perform(get("/directors"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$.[0].id").value(2))
                    .andExpect(jsonPath("$.[0].name").value("director-2"))
            ;
        }
    }
}