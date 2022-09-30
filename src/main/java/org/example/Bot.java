package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import commands.*;

public class Bot {

    public static TelegramBot bot;
    public static long chatId;
    public static String text;
    public static String userName;

    public Bot(String token) {
        bot = new TelegramBot(token);
    }

    private void process(Update update )
    {
        chatId = update.message().chat().id();
        text = update.message().text();
        userName = update.message().chat().firstName();
        if (text.equals("/about")) {
            About.execute();
        }
    }

    public void run(){
            bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
