package commands;

import domain.BotMessage;
import domain.Mailbox;
import infrastructure.DBRepository;
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
            bot.present(new BotMessage(MessagesTemplates.INCORRECT_ARGS.text, messageInfo.chatId()));
            return;
        }
        User user = messageInfo.user();
        if (user.getMailbox(args[0]) == null) {
            Mailbox mailbox = new Mailbox(args[0], args[1]);
            if (!bot.mailInterface.isCredentialsCorrect(mailbox)) {
                bot.present(new BotMessage(MessagesTemplates.MAIL_WRONG_LOGIN_PASSWORD.text, messageInfo.chatId()));
                return;
            }
            user.addNewMailbox(mailbox);
            DBRepository.userMailUpdate(messageInfo.user());
            bot.present(new BotMessage(MessagesTemplates.AUTH_COMPLETE.text, messageInfo.chatId()));
            return;
        }
        bot.present( new BotMessage(MessagesTemplates.AUTH_LOGIN_ALREADY_SEEN.text, messageInfo.chatId()));
    }
}
