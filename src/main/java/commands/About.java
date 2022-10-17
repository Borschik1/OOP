package commands;

import org.example.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import struct.MessageInfo;

public class About extends Command {

    public About(){
        super("about", "Получить информацию о функционале и авторах бота");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        bot.sendMessage(messageInfo.getChatId(), "Розенберг(КН-201), Гальперин(КН-201), продолжение следует");
    }
}
