package com.bot;

import com.bot.entity.Abbreviation;
import com.bot.repo.AbbreviationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {

    private final String ADD = "добавить ";
    private final String ADDED = "Добавлено успешно";

    @Autowired
    private AbbreviationRepo abbreviationRepo;

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            if (text.toLowerCase().contains(ADD)) {
                String[] pair = text.replace(ADD, "").split(" - ");
                Abbreviation abbreviation = new Abbreviation();
                abbreviation.setAbbr(pair[0]);
                abbreviation.setDecryption(pair[1]);
                abbreviationRepo.save(abbreviation);
                sendMsg(message, ADDED);
            } else {
                Optional<List<Abbreviation>> byAbbr = abbreviationRepo.findByAbbr(text);
                byAbbr.ifPresent(list -> list.forEach(desc -> sendMsg(message, desc.getDecryption())));
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage(message.getChatId(), text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "HomeAbbrBot";
    }

    public String getBotToken() {
        return "1456269510:AAGnxYGIj-7vSQjGIfS8Fh3491tVlmPHo2A";
    }
}

