package commands;

import org.example.*;
import struct.MessageInfo;

public class About extends Command {

    public About(){
        super("about", "Получить информацию о функционале и авторах бота");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        bot.present(messageInfo.chatId(), "Розенберг(КН-201), Гальперин(КН-201), продолжение следует");
    }
}
