package com.budimirov.telegrampersonalfinance.bot.handler;

import com.budimirov.telegrampersonalfinance.bot.State;
import com.budimirov.telegrampersonalfinance.model.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.budimirov.telegrampersonalfinance.util.TelegramUtil.createInlineKeyboardButton;
import static com.budimirov.telegrampersonalfinance.util.TelegramUtil.createMessageTemplate;

@Component
public class HelpHandler implements Handler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        // Create button to change name
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                createInlineKeyboardButton("\uD83D\uDCC8 Добавить доход", "/add_income"),
                createInlineKeyboardButton("\uD83D\uDCC9 Добавить расход", "/add_expense"));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        final var messageTemplate = createMessageTemplate(user);
        messageTemplate.setText("Выберите одно из следующих действий");
        messageTemplate.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(messageTemplate);
    }

    @Override
    public State operatedBotState() {
        return State.NONE;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}