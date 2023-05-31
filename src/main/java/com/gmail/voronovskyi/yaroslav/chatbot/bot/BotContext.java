package com.gmail.voronovskyi.yaroslav.chatbot.bot;

import com.gmail.voronovskyi.yaroslav.chatbot.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BotContext {

    private final ChatBot bot;
    private  final User user;
    private final String input;

    public static BotContext of(ChatBot bot, User user, String text) {
        return new BotContext(bot, user, text);
    }

    public BotContext(ChatBot bot, User user, String input) {
        this.bot = bot;
        this.user = user;
        this.input = input;
    }
}
