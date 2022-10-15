package commands;

import org.example.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class About {

    public About(){
        Help.add("/about", "Получить информацию о функционале и авторах бота");
    }

    public static void execute(long chatId){
        SendResponse response = Bot.bot.execute(new SendMessage(chatId, "Розенберг(КН-201), Гальперин(КН-201), продолжение следует"));
    }
}
