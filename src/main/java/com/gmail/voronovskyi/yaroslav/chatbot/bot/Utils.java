package com.gmail.voronovskyi.yaroslav.chatbot.bot;

import org.apache.commons.validator.routines.EmailValidator;

public class Utils {

    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
