package com.gmail.voronovskyi.yaroslav.chatbot.repository;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u From User u WHERE u.notified = false AND u.phone IS NOT NULL AND u.email IS NOT NULL")
    List<User> findNewUsers();
    User findByChatId(long id);
}
