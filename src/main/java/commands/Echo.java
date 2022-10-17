package commands;

import org.example.*;
import struct.MessageInfo;

public class Echo extends Command{

    public Echo(){
        super("echo", "Вывод введенной строчки");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        if (!messageInfo.text().equals("")) {
            bot.sendMessage(messageInfo.chatId(), messageInfo.text());
        } else {
            bot.sendMessage(messageInfo.chatId(), "Введите сообщение после команды");
        }
    }
}
