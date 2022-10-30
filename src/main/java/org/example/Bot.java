package org.example;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import commands.*;
import kotlin.Pair;
import struct.MessageInfo;

import java.util.HashMap;
import java.util.Iterator;

public class Bot {

    private final HashMap<String,Command> commands = new HashMap<>();
    private final IWriteRead writeRead;

    public Bot(IWriteRead writeRead) {
        this.writeRead = writeRead;
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
    public  void present(long chatId, String text) {
        writeRead.write(chatId, text);
    }

    public void process(String name, MessageInfo messageInfo) {

        var command = getCommandByName(name);
        if (command != null) {
            command.execute(messageInfo, this);
        } else {
            present(messageInfo.chatId(), "Такой команды не найдено");
        }
    }


}
