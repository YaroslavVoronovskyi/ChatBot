package com.gmail.voronovskyi.yaroslav.chatbot.bot;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import com.gmail.voronovskyi.yaroslav.chatbot.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@PropertySource("telegram.properties")
public class ChatBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatBot.class);

    private static final String BROADCAST = "broadcast ";
    private static final String LIST_USERS = "users";

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final IUserService userService;


    public ChatBot(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        User user = userService.findByChatId(chatId);

        if (checkIfAdminCommand(user, text)) {
            return;
        }

        BotContext context;
        BotState state;

        if (user == null) {
            state = BotState.getInitialState();

            user = User.builder()
                    .chatId(chatId)
                    .stateId(state.ordinal())
                    .build();
            userService.addUser(user);

            context = BotContext.of(this, user, text);
            state.enter(context);

            LOGGER.info("New user registered: {}", chatId);
        } else {
            context = BotContext.of(this, user, text);
            state = BotState.byId(user.getStateId());

            LOGGER.info("Update received for user in state {}", state);
        }
        state.handleInput(context);

        do {
            state = state.nextState();
            state.enter(context);
        } while (!state.isInputNeeded());

        user.setStateId(state.ordinal());
        userService.updateUser(user);
    }

    private boolean checkIfAdminCommand(User user, String text) {
        if (user == null || !user.getAdmin()) {
            return false;
        }

        if (text.startsWith(BROADCAST)) {
            LOGGER.info("Admin command received: " + BROADCAST);

            text = text.substring(BROADCAST.length());
            broadcast(text);

            return true;
        } else if (text.equals(LIST_USERS)) {
            LOGGER.info("Admin command received: " + LIST_USERS);
            listUsers(user);
            return true;
        }
        return false;
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException exception) {
            throw new RuntimeException("Something goes wrong!");
        }
    }

    private void listUsers(User admin) {
        StringBuilder stringBuilder = new StringBuilder("All users list:\r\n");
        List<User> usersList = userService.findAllUsers();

        usersList.forEach(user -> stringBuilder
                .append(' ')
                .append(user.getPhone())
                .append(' ')
                .append(user.getEmail())
                .append("\r\n")
        );

        sendMessage(admin.getChatId(), stringBuilder.toString());
    }

    private void broadcast(String text) {
        List<User> usersList = userService.findAllUsers();
        usersList.forEach(user -> sendMessage(user.getChatId(), text));
    }
}
