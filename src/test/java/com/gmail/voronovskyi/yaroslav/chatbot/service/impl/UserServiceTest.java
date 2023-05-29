package com.gmail.voronovskyi.yaroslav.chatbot.service.impl;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import com.gmail.voronovskyi.yaroslav.chatbot.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
}
