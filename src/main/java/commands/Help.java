package commands;

import org.example.Bot;
import struct.MessageInfo;

import java.util.Iterator;

public class Help extends Command{

    public Help(){
        super("help", "Получение информации о команде. Если после /help стоит название команды, то выводится ее описание.");
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
            answer.append("/").append(command.getName()).append(" ").append(command.getDescription()).append("\n");
        }
        bot.present(chatId, answer.toString());
    }
    private void helpCertain(long chatId, String name, Bot bot){
        var command = bot.getCommandByName(name);
        if (command != null){
            bot.present(chatId, "/" + name + " " + command.getDescription());
        }
        else{
            bot.present(chatId, "Такой команды не найдено");
        }
    }

}
