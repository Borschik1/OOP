package commands;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.example.Bot;
import struct.MessageInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Help extends Command{

    public Help(String prefix){
        super("help", "Получение информации о команде. Если после " + prefix + "help стоит название команды, то выводится ее описание.");
    };

    public void execute(MessageInfo messageInfo, Bot bot) {
        if (messageInfo.getText().equals("")) {
            helpAll(messageInfo.getChatId(), bot);
        } else {
            helpCertain(messageInfo.getChatId(), messageInfo.getText(), bot);
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
