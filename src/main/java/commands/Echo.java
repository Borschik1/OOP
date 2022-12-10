package commands;

import domain.BotMessage;
import org.example.*;
import struct.MessageInfo;

public class Echo extends Command{

    public Echo(){
        super("echo", "/echo <text>","Вывод введенной строчки");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        if (!messageInfo.text().equals("")) {
            bot.present(new BotMessage(messageInfo.text(), messageInfo.chatId()));
        } else {
            bot.present(new BotMessage("Введите сообщение после команды", messageInfo.chatId()));
        }
    }
}
