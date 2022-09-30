package commands;

import org.example.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class About {
    public static void execute(){
        SendResponse response = Bot.bot.execute(new SendMessage(Bot.chatId, "Вас интересовать не должно"));
    }
}
