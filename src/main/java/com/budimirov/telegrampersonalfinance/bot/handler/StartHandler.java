package com.budimirov.telegrampersonalfinance.bot.handler;

import com.budimirov.telegrampersonalfinance.bot.State;
import com.budimirov.telegrampersonalfinance.model.User;
import com.budimirov.telegrampersonalfinance.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.budimirov.telegrampersonalfinance.util.TelegramUtil.createMessageTemplate;

@Component
@RequiredArgsConstructor
public class StartHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final JpaUserRepository userRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        // Welcoming user
        SendMessage welcomeMessage = createMessageTemplate(user);
        welcomeMessage.setText(String.format(
                "Hola! I'm *%s*%nI am here to help you learn Java", botUsername
        ));
        // Asking to input name
        SendMessage registrationMessage = createMessageTemplate(user);
        registrationMessage.setText("In order to start our journey tell me your name");
        // Changing user state to "awaiting for name input"
        user.setBotState(State.ENTER_NAME);
        userRepository.save(user);

        return List.of(welcomeMessage, registrationMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
