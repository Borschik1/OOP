package commands;

import org.example.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import struct.MessageInfo;

public class Echo extends Command{

    public Echo(){
        super("echo", "Вывод введенной строчки");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        if (!messageInfo.getText().equals("")) {
            bot.sendMessage(messageInfo.getChatId(), messageInfo.getText());
        } else {
            bot.sendMessage(messageInfo.getChatId(), "Введите сообщение после команды");
        }
    }
}
