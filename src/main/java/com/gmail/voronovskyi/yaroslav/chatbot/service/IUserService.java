package com.gmail.voronovskyi.yaroslav.chatbot.service;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;

import java.util.List;

public interface IUserService {

    User findByChatId(long id);
    List<User> findAllUsers();
    List<User> findNewUsers();
    void addUser(User user);
    void updateUser(User user);
}
