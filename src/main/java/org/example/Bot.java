package org.example;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import commands.*;
import domain.User;
import domain.UserList;
import jakarta.mail.MessagingException;
import kotlin.Pair;
import struct.MessageInfo;

import java.util.HashMap;
import java.util.Iterator;
import interfaces.MailInterface;
import infrastructure.JakartaMailInterface;

public class Bot {

    private final HashMap<String,Command> commands = new HashMap<>();
    private final IWriteRead writeRead;
    public final MailInterface mailInterface;
    public UserList userList;

    public Bot(IWriteRead writeRead) {
        this.writeRead = writeRead;
        this.userList = new UserList();
        this.mailInterface = new JakartaMailInterface();
    }

    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }
    public Iterator<Command> getCommands() {
        return commands.values().iterator();
    }
    public Command getCommandByName(String name) {
        return commands.get(name);
    }
    public void present(long chatId, String text) {
        writeRead.write(chatId, text);
    }

    public void process(String name, MessageInfo messageInfo) throws MessagingException {

        var command = getCommandByName(name);
        if (command != null) {
            command.execute(messageInfo, this);
        } else {
            present(messageInfo.chatId(), "Такой команды не найдено");
        }
    }


}
