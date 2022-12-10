package commands;

import domain.BotMessage;
import org.example.Bot;
import struct.MessageInfo;
import enums.MessagesTemplates;
import domain.User;

import java.util.Objects;

public class DeleteMailbox extends Command {

    public DeleteMailbox(){
        super("delete_mail", "/delete_mail <address>","Удаляет данную почту");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        User user = messageInfo.user();
        String text = messageInfo.text();
        if (Objects.equals(text, "")) {
            bot.present(new BotMessage(MessagesTemplates.INCORRECT_ARGS.text, messageInfo.chatId()));
            return;
        }
        if (user.getMailbox(text) != null) {
            user.deleteMailbox(text);
            bot.present(new BotMessage(MessagesTemplates.DELETE_COMPLETE.text, messageInfo.chatId()));
            return;
        }
        bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
    }
}
