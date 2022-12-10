package commands;

import domain.BotMessage;
import org.example.Bot;
import struct.MessageInfo;

import java.util.Iterator;

public class Help extends Command{

    public Help(){
        super("help", "/help ~command~", "Получение информации о команде. При отсутствии команды после /help выводит информацию о всех командах");
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
            answer.append(command.getInputFormat()).append(" ").append(command.getDescription()).append("\n");
        }
        bot.present(new BotMessage(answer.toString(), chatId));
    }
    private void helpCertain(long chatId, String name, Bot bot){
        var command = bot.getCommandByName(name);
        if (command != null){
            bot.present(new BotMessage(command.getInputFormat() + " " + command.getDescription(), chatId));
        }
        else{
            bot.present(new BotMessage("Такой команды не найдено", chatId));
        }
    }

}
