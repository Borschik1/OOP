package org.example;

import commands.*;
import domain.BotMessage;
import domain.UserList;
import interfaces.IWriteRead;
import jakarta.mail.MessagingException;
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
    public void present(BotMessage message) {
        writeRead.write(message);
    }

    public void process(String name, MessageInfo messageInfo) throws MessagingException {

        var command = getCommandByName(name);
        if (command != null) {
            command.execute(messageInfo, this);
        } else {
            present(new BotMessage("Такой команды не найдено", messageInfo.chatId()));
        }
    }


}
