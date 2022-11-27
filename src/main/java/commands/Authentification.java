package commands;

import domain.Mailbox;
import org.example.Bot;
import struct.MessageInfo;
import enums.MessagesTemplates;
import domain.User;

public class Authentification extends Command {

    public Authentification(){
        super("new_mail", "/new_mail <address> <password>","Аутентификация для дальнейшего взаимодействия почтой");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        String[] args = messageInfo.text().split("\s");
        if (args.length != 2) {
            bot.present(messageInfo.chatId(), MessagesTemplates.INCORRECT_ARGS.text);
            return;
        }
        User user = messageInfo.user();
        if (user.getMailbox(args[0]) == null) {
            Mailbox mailbox = new Mailbox(args[0], args[1]);
            if (!bot.mailInterface.isCredentialsCorrect(mailbox)) {
                bot.present(messageInfo.chatId(), MessagesTemplates.MAIL_WRONG_LOGIN_PASSWORD.text);
                return;
            }
            user.addNewMailbox(mailbox);
            bot.present(messageInfo.chatId(), MessagesTemplates.AUTH_COMPLETE.text);
            return;
        }
        bot.present(messageInfo.chatId(), MessagesTemplates.AUTH_LOGIN_ALREADY_SEEN.text);
    }
}
