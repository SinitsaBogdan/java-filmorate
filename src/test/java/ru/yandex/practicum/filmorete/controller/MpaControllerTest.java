package ru.yandex.practicum.filmorete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorete.model.Mpa;
import ru.yandex.practicum.filmorete.sql.impl.RosterMpaDaoImpl;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MpaControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RosterMpaDaoImpl enumMpaDao;

    List<Mpa> listMpa = Arrays.asList(
            Mpa.builder().id(1).name("T").description("").build(),
            Mpa.builder().id(2).name("F").description("").build(),
            Mpa.builder().id(3).name("G").description("").build(),
            Mpa.builder().id(4).name("GD").description("").build()
    );

    @BeforeEach
    public void beforeEach() {
        enumMpaDao.delete();
        enumMpaDao.insert(
                listMpa.get(0).getId(),
                listMpa.get(0).getName(),
                listMpa.get(0).getDescription()
        );
        enumMpaDao.insert(
                listMpa.get(1).getId(),
                listMpa.get(1).getName(),
                listMpa.get(1).getDescription()
        );
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {

        @Test
        @DisplayName("Запрос списка записей в системе")
        public void testGetAll() throws Exception {
            mockMvc.perform(get("/mpa"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$.[0].id").value(1))
                    .andExpect(jsonPath("$.[0].name").value("T"))
                    .andExpect(jsonPath("$.[0].description").value(""))
            ;
        }

        @Test
        @DisplayName("Запрос существующей записи")
        public void testGetSearchId() throws Exception {
            mockMvc.perform(get("/mpa/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("T"))
                    .andExpect(jsonPath("$.description").value(""))
            ;
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {

        @Test
        @DisplayName("Добавление новой записи")
        public void testGetSearchId() throws Exception {
            mockMvc.perform(post("/mpa")
                            .content(objectMapper.writeValueAsString(listMpa.get(3)))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());

            mockMvc.perform(get("/mpa"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$.[2].id").value(6))
                    .andExpect(jsonPath("$.[2].name").value("GD"))
                    .andExpect(jsonPath("$.[2].description").value(""));
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {

        @Test
        @DisplayName("Обновление записи по id")
        public void testDeleteAll() throws Exception {
            mockMvc.perform(put("/mpa")
                            .content(objectMapper.writeValueAsString(
                                    Mpa
                                            .builder()
                                            .id(1)
                                            .name("W")
                                            .description("Up")
                                            .build()
                            ))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/mpa/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("W"))
                    .andExpect(jsonPath("$.description").value("Up"))
            ;
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {

        @Test
        @DisplayName("Удаление всего списка")
        public void testDeleteAll() throws Exception {
            mockMvc.perform(delete("/mpa"))
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/mpa"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0))
            ;
        }

        @Test
        @DisplayName("Удаление по id")
        public void testDeleteSearchId() throws Exception {
            mockMvc.perform(delete("/mpa/1"))
                    .andExpect(status().isOk())
            ;
            mockMvc.perform(get("/mpa"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
            ;
        }
    }
}
