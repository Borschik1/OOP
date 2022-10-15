package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import commands.*;

public class Bot {

    public static TelegramBot bot;
    public static Help help;
    public static About about;

    public Bot(String token) {
        bot = new TelegramBot(token);
        help = new Help();
        about = new About();
    }


    private void process(Update update )
    {
        long chatId;
        String text;
        String userName;

        chatId = update.message().chat().id();
        text = update.message().text();
        userName = update.message().chat().firstName();

        if (text.equals("/about")) {
            about.execute(chatId);
        }
        if (text.equals("/help")) {
            help.helpAll(chatId);
        } else if (text.substring(0, 5).equals("/help")) {
            help.helpCertain(chatId, text.substring(6, text.length()));
        }

    }

    public void run(){
            bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
