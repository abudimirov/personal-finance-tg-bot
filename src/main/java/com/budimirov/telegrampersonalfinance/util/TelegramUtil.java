package com.budimirov.telegrampersonalfinance.util;

import com.budimirov.telegrampersonalfinance.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramUtil {
    public static SendMessage createMessageTemplate(User user) {
        return createMessageTemplate(String.valueOf(user.getChatId()));
    }

    // Creating template of SendMessage with enabled Markdown
    public static SendMessage createMessageTemplate(String chatId) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        return message;
    }

    // Creating button
    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        var button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(command);
        return button;
    }
}
