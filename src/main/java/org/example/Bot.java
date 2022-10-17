package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import commands.*;
import kotlin.Pair;
import struct.MessageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Bot {

    private final TelegramBot bot;
    private final HashMap<String,Command> commands = new HashMap<>();

    public Bot(String token) {
        bot = new TelegramBot(token);
        addCommand(new About());
        addCommand(new Echo());
        addCommand(new Help(getPrefix()));
    }

    private void addCommand(Command command) {
        commands.put(command.getName(), command);
    }
    public Iterator<Command> getCommands() {
        return commands.values().iterator();
    }
    public Command getCommandByName(String name) {
        return commands.get(name);
    }
    public SendResponse sendMessage(long chatId, String text) {
        return bot.execute(new SendMessage(chatId, text));
    }
    public String getPrefix() {
        return "/";
    }
    private Pair<String, MessageInfo> parseMessage(Message message) {
        var chatId = message.chat().id();
        var text = message.text();
        var textSplit = text.split(" ", 2);
        var name = textSplit[0].substring(getPrefix().length());
        if (textSplit.length == 2) {
            text = textSplit[1];
        } else {
            text = "";
        }
        var userName = message.chat().firstName();
        var messageInfo = new MessageInfo(chatId, text, userName);
        return new Pair<>(name, messageInfo);
    }

    private void process(Update update) {

        var pair = parseMessage(update.message());
        var name = pair.getFirst();
        var messageInfo = pair.getSecond();

        var command = getCommandByName(name);
        if (command != null) {
            command.execute(messageInfo, this);
        } else {
            sendMessage(messageInfo.getChatId(), "Такой команды не найдено");
        }
    }

    public void run() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
