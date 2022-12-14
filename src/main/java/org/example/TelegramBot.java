package org.example;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import commands.Command;
import enums.MessagesTemplates;
import infrastructure.DBInterface;
import infrastructure.DBRepository;
import interfaces.IWriteRead;
import jakarta.mail.MessagingException;
import kotlin.Pair;
import struct.MessageInfo;
import java.util.ArrayList;

import domain.User;
import domain.BotMessage;

public class TelegramBot implements IWriteRead {
    private final com.pengrad.telegrambot.TelegramBot telegramBot;
    private final Bot bot;
    public TelegramBot(String token, ArrayList<Command> commands) throws MessagingException {
        telegramBot = new com.pengrad.telegrambot.TelegramBot(token);
        bot = new Bot(this);
        for (var command : commands) {
            bot.addCommand(command);
        }
        DBInterface dbInterface = new DBInterface();
        DBRepository.createUsersFromDB(dbInterface.getAllUserDBO(), bot);
    }
    public void run() {
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
    private void process(Update update) {
        Pair<String, MessageInfo> message;
        if (update.callbackQuery() == null) {
            message = parseMessage(update.message().chat().id(), update.message().text());
        } else {
            message = parseMessage(update.callbackQuery().message().chat().id(),
                    update.callbackQuery().data());
        }
        try {
            bot.process(message.getFirst(), message.getSecond());
        } catch (MessagingException e) {
            bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, message.getSecond().chatId()));
        }
    }
    private Pair<String, MessageInfo> parseMessage(Long chatId, String text) {
        var textSplit = text.split(" ", 2);
        var name = textSplit[0].substring(1);
        if (textSplit.length == 2) {
            text = textSplit[1];
        } else {
            text = "";
        }
        User user = bot.userList.getUserById(chatId);
        var messageInfo = new MessageInfo(chatId, text, user);
        return new Pair<>(name, messageInfo);
    }

    @Override
    public void write(BotMessage message) {
        if (message.getButtons() == null) {
            telegramBot.execute(new SendMessage(message.getChatId(), message.getText()));
            return;
        }
        telegramBot.execute(new SendMessage(message.getChatId(), message.getText()).replyMarkup(message.getButtons()));
    }
}
