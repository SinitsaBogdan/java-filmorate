package ru.yandex.practicum.filmorete.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorete.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    static User user1;
    static User user2;
    static User user3;

    @BeforeAll
    static void beforeAll() {
        user1 = User.builder()
                .name("Богдан")
                .login("Sinitsa")
                .birthday(LocalDate.of(1997, 4, 11))
                .email("a@a.com")
                .build();

        user2 = User.builder()
                .name("Андрей")
                .login("Andrey")
                .birthday(LocalDate.of(1997, 4, 11))
                .email("andr@a.com")
                .build();

        user3 = User.builder()
                .name("Елена")
                .login("Elena")
                .birthday(LocalDate.of(1997, 4, 11))
                .email("elena@a.com")
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        service.storage.addUser(user1);
        service.storage.addUser(user2);
        service.storage.addUser(user3);
        service.addFriend(user1, user2);
        service.addFriend(user1, user3);
        service.addFriend(user2, user3);
    }

    @AfterEach
    public void afterEach() {
        service.storage.clear();
    }

    @Test
    @DisplayName("addFriendsTest")
    public void addFriendsTest() {

        assertEquals(user3, service.getFriends(user2).get(0));
    }

    @Test
    @DisplayName("removeFriendsTest")
    public void removeFriendsTest() {

        service.removeFriend(user1, user3);
        assertEquals(user2, service.getFriends(user1).get(0));
    }

    @Test
    @DisplayName("getFriendsTest")
    public void getFriendsTest() {

        assertEquals(user2, service.getFriends(user1).get(0));
        assertEquals(user3, service.getFriends(user1).get(1));
    }

    @Test
    @DisplayName("getSharedFriendsTest")
    public void getSharedFriendsTest() {

        assertEquals(user3, service.getSharedFriends(user1, user2).get(0));
    }
}