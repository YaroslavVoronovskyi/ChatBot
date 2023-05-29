package com.gmail.voronovskyi.yaroslav.chatbot.service.impl;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import com.gmail.voronovskyi.yaroslav.chatbot.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final static long CHAT_ID = 1L;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnExpectedUserByChatId() {
        Mockito.when(userRepository.findByChatId(CHAT_ID)).thenReturn(createTestUser());
        assertEquals(userService.findByChatId(CHAT_ID), createTestUser());
    }

    @Test
    public void shouldReturnExpectedAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(createTestUser()));
        assertEquals(userService.findAllUsers(), List.of(createTestUser()));
    }

    @Test
    public void shouldReturnExpectedNewUsers() {
        Mockito.when(userRepository.findNewUsers()).thenReturn(List.of(createTestNewUser()));
        assertEquals(userService.findNewUsers(), List.of(createTestNewUser()));
    }

    @Test
    public void shouldAddNewUser() {
        User user = createTestNewUser();
        user.setAdmin(false);
        userService.addUser(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void shouldUpdateUser() {
        User user = createTestNewUser();
        user.setAdmin(false);
        userService.updateUser(user);
        Mockito.verify(userRepository).save(user);
    }

    private User createTestUser() {
        return User.builder()
                .id(1L)
                .chatId(CHAT_ID)
                .phone("+380976714492")
                .email("yaroslav.voronovskyi@gmail.com")
                .admin(true)
                .notified(false)
                .build();
    }

    private User createTestNewUser() {
        return User.builder()
                .id(1L)
                .chatId(CHAT_ID)
                .phone("+380976714492")
                .email("yaroslav.voronovskyi@gmail.com")
                .admin(true)
                .notified(true)
                .build();
    }
}
