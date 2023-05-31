package com.gmail.voronovskyi.yaroslav.chatbot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gmail.voronovskyi.yaroslav.chatbot.repository")
@ComponentScan("com.gmail.voronovskyi.yaroslav.chatbot")
public class AppConfig {

}
