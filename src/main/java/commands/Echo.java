package commands;

import org.example.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class Echo {

    public Echo(){
        Help.add("/echo", "Вывод введенной строчки");
    }

    public static void execute(long chatId, String message){
        if (!message.equals("")) {
            SendResponse response = Bot.bot.execute(new SendMessage(chatId, message.substring(1, message.length())));
        } else {
            SendResponse response = Bot.bot.execute(new SendMessage(chatId, "Введите сообщение после команды /echo"));
        }
    }
}
