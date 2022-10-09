package com.budimirov.telegrampersonalfinance.bot.handler;

import com.budimirov.telegrampersonalfinance.bot.State;
import com.budimirov.telegrampersonalfinance.model.User;
import com.budimirov.telegrampersonalfinance.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static com.budimirov.telegrampersonalfinance.util.TelegramUtil.createInlineKeyboardButton;
import static com.budimirov.telegrampersonalfinance.util.TelegramUtil.createMessageTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculationHandler implements Handler {
    public static final String ADD_INCOME = "/add_income";
    public static final String ADD_EXPENSE = "/add_expense";

    private final JpaUserRepository userRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if (message.startsWith(ADD_INCOME)) {
            // action performed on callback with add income
            SendMessage addIncomeText = createMessageTemplate(user);
            addIncomeText.setText("Здесь будет процесс добавления доходов");
            return List.of(addIncomeText);
        } else if (message.startsWith(ADD_EXPENSE)) {
            // action performed on callback with incorrect answer
            SendMessage addExpenseText = createMessageTemplate(user);
            addExpenseText.setText("Здесь будет процесс добавления расходов");
            return List.of(addExpenseText);
        } else {
            return startFromBegining(user);
        }
    }

    private List<PartialBotApiMethod<? extends Serializable>> startFromBegining(User user) {
        user.setBotState(State.PLAYING_QUIZ);
        userRepository.save(user);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                createInlineKeyboardButton("\uD83D\uDCC8 Добавить доход", ADD_INCOME),
                createInlineKeyboardButton("\uD83D\uDCC9 Добавить расход", ADD_EXPENSE));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        final var messageTemplate = createMessageTemplate(user);
        messageTemplate.setText("Выберите одно из следующих действий");
        messageTemplate.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(messageTemplate);
    }

    @Override
    public State operatedBotState() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(ADD_INCOME, ADD_EXPENSE);
    }
}
