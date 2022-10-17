package commands;

import org.example.Bot;
import struct.MessageInfo;

import java.util.Iterator;

public class Help extends Command{

    public Help(String prefix){
        super("help", "Получение информации о команде. Если после " + prefix + "help стоит название команды, то выводится ее описание.");
    };

    public void execute(MessageInfo messageInfo, Bot bot) {
        if (messageInfo.text().equals("")) {
            helpAll(messageInfo.chatId(), bot);
        } else {
            helpCertain(messageInfo.chatId(), messageInfo.text(), bot);
        }
    }
    private void helpAll(long chatId, Bot bot){
        StringBuilder answer = new StringBuilder();
        for (Iterator<Command> it = bot.getCommands(); it.hasNext(); ) {
            Command command = it.next();
            answer.append(bot.getPrefix()).append(command.getName()).append(" ").append(command.getDescription()).append("\n");
        }
        bot.sendMessage(chatId, answer.toString());
    }
    private void helpCertain(long chatId, String name, Bot bot){
        var command = bot.getCommandByName(name);
        if (command != null){
            bot.sendMessage(chatId, bot.getPrefix() + name + " " + command.getDescription());
        }
        else{
            bot.sendMessage(chatId, "Такой команды не найдено");
        }
    }

}
