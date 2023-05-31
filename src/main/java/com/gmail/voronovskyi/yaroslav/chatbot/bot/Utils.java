package com.gmail.voronovskyi.yaroslav.chatbot.bot;

import org.apache.commons.validator.EmailValidator;

public class Utils {

    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

//    public static boolean isValidPhoneNumber(String phoneNumber) {
//        String matcher = "/^(\\([0-9]{3}\\)\\s*|[0-9]{3}\\-)[0-9]{3}-[0-9]{4}$/";
//        return true;
//    }
}
