package com.bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Configuration
public class Config {
    @Bean
    public LongPollingBot getBot() throws TelegramApiRequestException {
        TelegramBotsApi api = new TelegramBotsApi();
        LongPollingBot myBot = new Bot();
        api.registerBot(myBot);
        return myBot;
    }
}
