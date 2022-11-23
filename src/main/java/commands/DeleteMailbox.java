package commands;

import org.example.Bot;
import struct.MessageInfo;
import enums.MessagesTemplates;
import domain.User;

import java.util.Objects;

public class DeleteMailbox extends Command {

    public DeleteMailbox(){
        super("delete_mail", "Удаляет данную почту");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        User user = messageInfo.user();
        String text = messageInfo.text();
        if (Objects.equals(text, "")) {
            bot.present(messageInfo.chatId(), MessagesTemplates.INCORRECT_ARGS.text);
            return;
        }
        if (user.getMailbox(text) == null) {
            user.deleteMailbox(text);
            return;
        }
        bot.present(messageInfo.chatId(), MessagesTemplates.MAIL_NOT_FOUND.text);
    }
}
