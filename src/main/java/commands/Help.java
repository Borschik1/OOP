package commands;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.example.Bot;

import java.util.HashMap;
import java.util.Map;

public class Help {

    public static HashMap<String,String> descriptions = new HashMap<String,String>();

    public Help(){
        descriptions.put("/help", "Получение информации о всех командах");
        descriptions.put("/help <command>", "Получение информации о конкретной команде");
    };
    public static void add(String key, String value){
        descriptions.put(key, value);
    }
    public static void helpAll(long chatId){
        StringBuilder answer = new StringBuilder();
        for (String key : descriptions.keySet()){
            answer.append(key).append(" ").append(descriptions.get(key)).append("\n");
        }
        SendResponse response = Bot.bot.execute(new SendMessage(chatId, answer.toString()));
    }
    public static void helpCertain(long chatId, String command){
        if (descriptions.containsKey(command)){
            SendResponse response = Bot.bot.execute(new SendMessage(chatId, command + " " + descriptions.get(command)));
        }
        else{
            SendResponse response = Bot.bot.execute(new SendMessage(chatId, "Такой команды не найдено"));
        }
    }

}
