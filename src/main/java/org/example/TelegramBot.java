package org.example;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import commands.Command;
import enums.MessagesTemplates;
import jakarta.mail.MessagingException;
import kotlin.Pair;
import struct.MessageInfo;
import java.util.ArrayList;
import domain.UserList;
import domain.User;

public class TelegramBot implements IWriteRead{
    private final com.pengrad.telegrambot.TelegramBot telegramBot;
    private final Bot bot;
    public TelegramBot(String token, ArrayList<Command> commands) {
        telegramBot = new com.pengrad.telegrambot.TelegramBot(token);
        bot = new Bot(this);
        for (var command : commands) {
            bot.addCommand(command);
        }
    }
    public void run() {
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
    private void process(Update update) {
        var message = parseMessage(update.message());
        try {
            bot.process(message.getFirst(), message.getSecond());
        } catch (MessagingException e) {
            bot.present(message.getSecond().chatId(), MessagesTemplates.MAIL_NOT_FOUND.text);
        }
    }
    private Pair<String, MessageInfo> parseMessage(Message message) {
        var chatId = message.chat().id();
        var text = message.text();
        var textSplit = text.split(" ", 2);
        var name = textSplit[0].substring(1);
        if (textSplit.length == 2) {
            text = textSplit[1];
        } else {
            text = "";
        }
        User user = bot.userList.getUserById(chatId);
        user.setName(message.chat().firstName());
        var messageInfo = new MessageInfo(chatId, text, user);
        return new Pair<>(name, messageInfo);
    }

    @Override
    public void write(long chatId, String text) {
        telegramBot.execute(new SendMessage(chatId, text));
    }
}
