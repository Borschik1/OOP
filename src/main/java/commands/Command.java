package commands;

import jakarta.mail.MessagingException;
import org.example.Bot;
import struct.MessageInfo;

abstract public class Command {

    private final String name;
    private final String inputFormat;
    private final String description;

    public Command(String name, String inputFormat, String description) {
        this.name = name;
        this.inputFormat = inputFormat;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getInputFormat() { return inputFormat; }

    public String getDescription() {
        return description;
    }

    abstract public void execute(MessageInfo messageInfo, Bot bot) throws MessagingException;

}
