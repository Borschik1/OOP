package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class Bot {

    TelegramBot bot;

    public Bot(String token) {
        bot = new TelegramBot(token);
    }

    private void process(Update update )
    {
        // Send messages
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String userName = update.message().chat().firstName();
        if (text.equals("/about")) {
            SendResponse response = bot.execute(new SendMessage(chatId, "Вас интересовать не должно"));
        }
    }

    public void run(){
            bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
