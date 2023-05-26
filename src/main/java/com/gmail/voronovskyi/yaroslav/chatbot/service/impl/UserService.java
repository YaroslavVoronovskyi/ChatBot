package com.gmail.voronovskyi.yaroslav.chatbot.service.impl;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import com.gmail.voronovskyi.yaroslav.chatbot.repository.IUserRepository;
import com.gmail.voronovskyi.yaroslav.chatbot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByChatId(long id) {
        return userRepository.findByChatId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findNewUsers() {
        List<User> users = userRepository.findNewUsers();
        users.forEach((user) -> user.setNotified(true));
        userRepository.saveAll(users);
        return users;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setAdmin(userRepository.count() == 0);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
