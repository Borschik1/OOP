package commands;

import org.example.Bot;
import struct.MessageInfo;

abstract public class Command {

    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    abstract public void execute(MessageInfo messageInfo, Bot bot);

}
