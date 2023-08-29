package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Director;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class DirectorControllerTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    /*@Autowired
    private DirectorDao directorDao;*/

    private final Director duplicate = Director.builder().id(1L).name("director-1").build();

    @BeforeEach
    public void beforeEach() {
        //directorDao.delete();
        //directorDao.insert(1L,"director-1");
        //directorDao.insert(2L,"director-2");
        //directorDao.insert(3L,"director-3");
    }

    @Nested
    @DisplayName("GET")
    public class MethodGet {
        @Test
        @DisplayName("Получение режиссёра: ID 1")
        void methodGet_DirectorId1Test() {
        }

        @Test
        @DisplayName("Запрос режиссёра: ID 9999")
        public void methodGet_DirectorId9999Test() throws Exception {

        }

        @Test
        @DisplayName("Запрос режиссёра: ID -1")
        public void methodGet_DirectorIdMinus1Test() throws Exception {

        }


        @Test
        @DisplayName("Получение всех режиссёров")
        void methodGet_AllDirectors() {
        }
    }

    @Nested
    @DisplayName("POST")
    public class MethodPost {
        @Test
        @DisplayName("Проверка добавления дубликата режиссёра")
        void methodPost_NewDirectorValidTrue_AndDoubleFalseTest() throws Exception {
            /*mockMvc.perform(post("/directors")
                            .content(objectMapper.writeValueAsString(duplicate))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is4xxClientError())
            ;*/
        }

        @Test
        @DisplayName("Добавление нового пользователя - name : null")
        public void methodPost_NewDirectorValidTrue_NameNullTest() throws Exception {
            Director director = Director.builder()
                    .id(99L)
                    .name(null)
                    .build();
        }
    }

    @Nested
    @DisplayName("PUT")
    public class MethodPut {
        @Test
        @DisplayName("Обновление режиссёра")
        void methodPut_DirectorValidTrueTest() throws Exception {
        }

        @Test
        @DisplayName("Обновление режиссёра - id : null")
        public void methodPut_DirectorValidFalse_IdNullTest() throws Exception {
            Director director = Director.builder()
                    .id(null)
                    .name("update")
                    .build();
        }

        @Test
        @DisplayName("Обновление режиссёра - id : 99")
        public void methodPut_DirectorValidFalse_IdNotInCollectionsTest() throws Exception {
            Director director = Director.builder()
                    .id(99L)
                    .name("update")
                    .build();
        }

        @Test
        @DisplayName("Обновление режиссёра - name : empty")
        public void methodPut_DirectorValidFalse_NameEmptyTest() throws Exception {
            Director director = Director.builder()
                    .id(1L)
                    .name("")
                    .build();
        }

        @Test
        @DisplayName("Обновление режиссёра - name : null")
        public void methodPut_DirectorValidFalse_NameNullTest() throws Exception {
            Director director = Director.builder()
                    .id(1L)
                    .name(null)
                    .build();
        }
    }

    @Nested
    @DisplayName("DELETE")
    public class MethodDelete {
        @Test
        @DisplayName("Удаление режиссёра по ID")
        void methodDelete_DeleteDirectorByIdTest() {
        }
    }

}