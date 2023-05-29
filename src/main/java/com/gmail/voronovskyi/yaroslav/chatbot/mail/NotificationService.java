package com.gmail.voronovskyi.yaroslav.chatbot.mail;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import com.gmail.voronovskyi.yaroslav.chatbot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:telegram.property")
public class NotificationService {

    private final IUserService userService;
    private final JavaMailSender emailSender;

    @Value("${bot.email.subject}")
    private String emailSubject;

    @Value("${bot.mail.from}")
    private String emailFrom;

    @Value("${bot.mail.to}")
    private String emailTo;

    @Autowired
    public NotificationService(IUserService userService, JavaMailSender emailSender) {
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @Scheduled(fixedRate = 10000)
    public void sendNewApplications() {
        List<User> usersList = userService.findNewUsers();
        if (usersList.size() == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        usersList.forEach(user -> stringBuilder
                .append("Phone: ")
                .append(user.getPhone())
                .append("\r\n")
                .append(user.getEmail())
                .append("\r\n\r\n")
        );

        sendEmail(stringBuilder.toString());
    }

    private void sendEmail(String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailTo);
        message.setFrom(emailFrom);
        message.setSubject(emailSubject);
        message.setText(text);

        emailSender.send(message);
    }
}

