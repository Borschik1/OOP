package commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import domain.BotMessage;
import domain.SendLetter;
import enums.MessagesTemplates;
import org.example.Bot;
import struct.MessageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WriteLetter extends Command {

    public WriteLetter(){
        super("new_letter", "/new_letter <address> <name>", "Создать новое именное письмо из заданного ящика");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        String[] args = messageInfo.text().split("\s");
        if (args.length != 2) {
            bot.present(new BotMessage(MessagesTemplates.INCORRECT_ARGS.text, messageInfo.chatId()));
            return;
        }
        SendLetter letter = new SendLetter(args[1], messageInfo.user().getMailbox(args[0]));
        messageInfo.user().addNewLetter(letter);
    }
}
